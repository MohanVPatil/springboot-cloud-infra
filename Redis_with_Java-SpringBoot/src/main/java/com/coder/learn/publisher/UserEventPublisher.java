package com.coder.learn.publisher;

import com.coder.learn.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserEventPublisher {

	private final RedisTemplate<String, String> redisTemplate;
	private final ChannelTopic topic;
	private final ObjectMapper objectMapper;

	@Autowired
	public UserEventPublisher(RedisTemplate<String, String> redisTemplate, ChannelTopic topic,
			ObjectMapper objectMapper) {
		this.redisTemplate = redisTemplate;
		this.topic = topic;
		this.objectMapper = objectMapper;
	}

	public void publish(UserEvent event) {
		try {
			// Convert UserEvent to JSON string
			String message = objectMapper.writeValueAsString(event);
			redisTemplate.convertAndSend(topic.getTopic(), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
