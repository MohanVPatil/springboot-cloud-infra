package com.coder.learn.service;

import com.coder.learn.dto.UserProfileCacheDTO;
import com.coder.learn.entity.User;
import com.coder.learn.entity.UserAccess;
import com.coder.learn.entity.UserActivityLog;
import com.coder.learn.event.UserEvent;
import com.coder.learn.publisher.UserEventPublisher;
import com.coder.learn.repository.UserAccessRepository;
import com.coder.learn.repository.UserActivityLogRepository;
import com.coder.learn.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private static final String PREFIX = "USER_";
    private static final Logger log = LoggerFactory.getLogger(User.class);
    @Autowired
    private final UserEventPublisher eventPublisher;
    @Autowired
    @Qualifier("redisTemplateUser")
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private RedisTemplate<String, UserProfileCacheDTO> userProfileCacheRedisTemplate;
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private UserActivityLogRepository userActivityLogRepository;
    @Autowired
    @Qualifier("customStringRedisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserService(UserEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    // method to fetch userById initial
    public User getUserById(Long id) {
        String key = PREFIX + id;
        User cachedUser = redisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            return cachedUser;
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            redisTemplate.opsForValue().set(key, user, 42, TimeUnit.SECONDS);
            return user;
        }
        return null;
    }

    // method to fetch usersById with logging and caching
    public User findUserById(Long id) {
        User user = null;
        try {
            user = redisTemplate.opsForValue().get(PREFIX + id);

            if (user == null) {
                log.info("Cache miss for user ID: " + id);
                user = userRepository.findById(id).orElse(null);
                if (user != null) {
                    redisTemplate.opsForValue().set(PREFIX + id, user, 1, TimeUnit.HOURS);
                }
            } else {
                log.info("Cache hit for user ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error while getting user from cache or DB for ID: " + id, e);
        }

        return user;
    }

    /*
     * // method to add users directly to the db. public User addUser(User user) {
     * String key = PREFIX + user.getId(); User savedUser =
     * userRepository.save(user); redisTemplate.opsForValue().set(key, savedUser,
     * 20, TimeUnit.SECONDS); return savedUser; }
     */

    // method to edit users directly to the db.
    public User editUserById(Long id, User userDetails) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUserName(userDetails.getUserName());
            User modifiedUser = userRepository.save(user);
            String key = PREFIX + modifiedUser.getUserId();
            redisTemplate.opsForValue().set(key, modifiedUser, 42, TimeUnit.SECONDS);
            return modifiedUser;
        }
        return null;
    }

    // method to delete users directly from the db.
    public User deleteUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
            String key = PREFIX + id;
            redisTemplate.delete(key);
        }
        return null;
    }

    // method to add users firstly to the redis cache and then to the db.
    public User addUser(User user) {
        String userKey = PREFIX + user.getUserId();
        String triggerKey = "trigger:user:" + user.getUserId();
        redisTemplate.opsForValue().set(userKey, user);
        stringRedisTemplate.opsForValue().set(triggerKey, "", 42, TimeUnit.SECONDS);

        UserEvent event = new UserEvent();
        // event.setUserId(user.getId());
        event.setAction("CREATED");
        event.setTimestamp(Instant.now().toString());
        eventPublisher.publish(event);

        return user;
    }

    // Write the user to the database when the trigger expires.
    public void writeUserToDatabase(String userId) {
        String userKey = PREFIX + userId;
        User user = redisTemplate.opsForValue().get(userKey);
        if (user != null) {
            userRepository.save(user);
            redisTemplate.delete(userKey);
            logger.info("User written to DB and removed from Redis: " + user);
        } else {
            logger.warn("No user found in Redis for ID: " + userId);
        }
    }

    // method to update users first to cache and then directly to the db.
    public User updateUserById(Long id, User updatedData) {
        String userKey = PREFIX + id;
        String triggerKey = "trigger:user:" + id;
        User existingUser = redisTemplate.opsForValue().get(userKey);
        if (existingUser == null) {
            existingUser = userRepository.findById(id).orElse(null);
        }
        if (existingUser == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        existingUser.setUserName(updatedData.getUserName());
        redisTemplate.opsForValue().set(userKey, existingUser);
        stringRedisTemplate.opsForValue().set(triggerKey, "", 42, TimeUnit.SECONDS);

        UserEvent event = new UserEvent();
        // event.setUserId(id);
        event.setAction("UPDATED");
        event.setTimestamp(Instant.now().toString());
        eventPublisher.publish(event);

        return existingUser;
    }

    public UserProfileCacheDTO getUserProfile(Long userId) {
        String cacheKey = "USER_PROFILE_" + userId;
        // Try to fetch from Redis
        UserProfileCacheDTO cachedProfile = userProfileCacheRedisTemplate.opsForValue().get(cacheKey);
        if (cachedProfile != null) {
            log.info("Cache hit for user profile ID: {}", userId);
            return cachedProfile;
        }

        log.info("Cache miss for user profile ID: {}", userId);

        // Otherwise fetch from DB
        User user = userRepository.findById(userId).orElse(null);
        UserAccess access = userAccessRepository.findByUserId(userId);
        System.out.println(access.getAccessType());
        System.out.println(access.getPermissions());
        UserActivityLog logEntry = userActivityLogRepository.findTopByUserIdOrderByTimestampDesc(userId);

        if (user == null) {
            return null;
        }

        UserProfileCacheDTO profile = new UserProfileCacheDTO(user, access, logEntry);
        // Store in Redis for 10 minutes
        userProfileCacheRedisTemplate.opsForValue().set(cacheKey, profile, 10, TimeUnit.MINUTES);

        return profile;
    }

}
