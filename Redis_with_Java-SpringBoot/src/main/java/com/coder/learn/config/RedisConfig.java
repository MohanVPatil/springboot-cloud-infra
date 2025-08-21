package com.coder.learn.config;

import com.coder.learn.dto.UserProfileCacheDTO;
import com.coder.learn.entity.User;
import com.coder.learn.entity.UserAccess;
import com.coder.learn.entity.UserActivityLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration


public class RedisConfig {

    private <T> RedisTemplate<String, T> createRedisTemplate(RedisConnectionFactory connectionFactory, Class<T> clazz) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer<T> serializer = new Jackson2JsonRedisSerializer<>(clazz);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //Deprecated but still required — suppress warning here
        @SuppressWarnings("deprecation")
        Jackson2JsonRedisSerializer<T> safeSerializer = serializer;
        safeSerializer.setObjectMapper(objectMapper);

        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }


    @Bean(name = "redisObjectTemplate")
    public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory, Object.class);
    }

    @Bean(name = "redisTemplateUser")
    public RedisTemplate<String, User> redisTemplateUser(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory, User.class);
    }

    @Bean(name = "redisTemplateUserAccess")
    public RedisTemplate<String, UserAccess> redisTemplateUserAccess(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory, UserAccess.class);
    }

    @Bean(name = "redisTemplateUserActivityLog")
    public RedisTemplate<String, UserActivityLog> redisTemplateUserActivityLog(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory, UserActivityLog.class);
    }

    @Bean(name = "userProfileCacheRedisTemplate")
    public RedisTemplate<String, UserProfileCacheDTO> userProfileCacheRedisTemplate(RedisConnectionFactory connectionFactory) {
        return createRedisTemplate(connectionFactory, UserProfileCacheDTO.class);
    }

    @Bean
    @Primary
    public RedisTemplate<String, String> customStringRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
