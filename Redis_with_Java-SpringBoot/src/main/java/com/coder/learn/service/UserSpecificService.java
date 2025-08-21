package com.coder.learn.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.coder.learn.entity.FullUserInfoRequest;
import com.coder.learn.entity.User;
import com.coder.learn.entity.UserAccess;
import com.coder.learn.entity.UserActivityLog;
import com.coder.learn.repository.UserAccessRepository;
import com.coder.learn.repository.UserActivityLogRepository;
import com.coder.learn.repository.UserRepository;

@Service
public class UserSpecificService {

	@Autowired
	@Qualifier("redisTemplateUser")
	private RedisTemplate<String, User> redisTemplateUser;

	@Autowired
	@Qualifier("redisTemplateUserAccess")
	private RedisTemplate<String, UserAccess> redisTemplateUserAccess;

	@Autowired
	@Qualifier("redisTemplateUserActivityLog")
	private RedisTemplate<String, UserActivityLog> redisTemplateUserActivityLog;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAccessRepository userAccessRepository;

	@Autowired
	private UserActivityLogRepository userActivityLogRepository;

	public String syncUserFromCacheOrInsert(FullUserInfoRequest request) {
		Long userId = request.getUser().getUserId();

		// Check if user data exists in Redis cache
		User cachedUser = (User) redisTemplateUser.opsForValue().get("USER_" + userId);
		UserAccess cachedAccess = (UserAccess) redisTemplateUserAccess.opsForValue().get("ACCESS_" + userId);
		UserActivityLog cachedLog = (UserActivityLog) redisTemplateUserActivityLog.opsForValue().get("LOG_" + userId);

		if (cachedUser != null && cachedAccess != null && cachedLog != null) {
			// Data found in Redis, then update the database
			updateDatabaseFromCache(cachedUser, cachedAccess, cachedLog, request);
			return "Updated database from cache data.";
		} else {
			// Data not found in Redis, insert new data into both Redis and DB
			insertNewDataIntoCacheAndDB(request);
			return "Inserted new data into both database and cache.";
		}
	}

	private void updateDatabaseFromCache(User cachedUser, UserAccess cachedAccess, UserActivityLog cachedLog,
			FullUserInfoRequest request) {
		// Update user data in DB
		cachedUser.setUserName(request.getUser().getUserName());
		userRepository.save(cachedUser);

		// Update user access data in DB
		cachedAccess.setAccessType(request.getUserAccess().getAccessType());
		cachedAccess.setPermissions(request.getUserAccess().getPermissions());
		userAccessRepository.save(cachedAccess);

		// Update user activity log in DB
		cachedLog.setAction(request.getUserActivityLog().getAction());
		cachedLog.setTimestamp(request.getUserActivityLog().getTimestamp());
		userActivityLogRepository.save(cachedLog);

		// Optionally update cache after DB update
		redisTemplateUser.opsForValue().set("USER_" + cachedUser.getUserId(), cachedUser, 10, TimeUnit.MINUTES);
		redisTemplateUserAccess.opsForValue().set("ACCESS_" + cachedUser.getUserId(), cachedAccess, 10, TimeUnit.MINUTES);
		redisTemplateUserActivityLog.opsForValue().set("LOG_" + cachedUser.getUserId(), cachedLog, 10, TimeUnit.MINUTES);
	}

	private void insertNewDataIntoCacheAndDB(FullUserInfoRequest request) {
		User user = request.getUser();
		UserAccess access = request.getUserAccess();
		UserActivityLog log = request.getUserActivityLog();

		// Insert user data into DB
		userRepository.save(user);

		// Insert user access data into DB
		userAccessRepository.save(access);

		// Insert user activity log data into DB
		userActivityLogRepository.save(log);

		// Insert data into Redis cache
		redisTemplateUser.opsForValue().set("USER_" + user.getUserId(), user, 10, TimeUnit.MINUTES);
		redisTemplateUserAccess.opsForValue().set("ACCESS_" + user.getUserId(), access, 10, TimeUnit.MINUTES);
		redisTemplateUserActivityLog.opsForValue().set("LOG_" + user.getUserId(), log, 10, TimeUnit.MINUTES);
	}

}
