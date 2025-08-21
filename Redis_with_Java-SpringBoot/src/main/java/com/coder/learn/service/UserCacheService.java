package com.coder.learn.service;

import com.coder.learn.entity.User;
import com.coder.learn.entity.UserAccess;
import com.coder.learn.entity.UserActivityLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserCacheService {

    private static final long CACHE_EXPIRATION_MINUTES = 10;
    @Autowired
    @Qualifier("redisTemplateUser")
    private RedisTemplate<String, User> redisTemplate;
    @Autowired
    private RedisTemplate<String, UserAccess> userAccessRedisTemplate;
    @Autowired
    private RedisTemplate<String, UserActivityLog> userActivityLogRedisTemplate;

    public void cacheUserDetails(User user, UserAccess access, UserActivityLog activityLog) {
        if (user != null) {
            redisTemplate.opsForValue().set("USER_" + user.getUserId(), user, CACHE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        }
        if (access != null) {
            userAccessRedisTemplate.opsForValue().set("USER_ACCESS_" + user.getUserId(), access, CACHE_EXPIRATION_MINUTES,
                    TimeUnit.MINUTES);
        }
        if (activityLog != null) {
            userActivityLogRedisTemplate.opsForValue().set("USER_ACTIVITY_" + user.getUserId(), activityLog,
                    CACHE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        }
    }
}
