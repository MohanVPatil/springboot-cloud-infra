package com.coder.learn.listner;

import com.coder.learn.entity.User;
import com.coder.learn.repository.UserRepository;
import com.coder.learn.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

	@Autowired
	private RedisTemplate<String, User> redisTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer, UserService userService) {
		super(listenerContainer);
		listenerContainer.addMessageListener(this, new PatternTopic("__keyevent@0__:expired"));
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String expiredKey = message.toString();
		if (expiredKey.startsWith("trigger:user:")) {
			String userId = expiredKey.replace("trigger:user:", "");
			User user = redisTemplate.opsForValue().get("USER_" + userId);

			if (user != null) {
				Optional<User> existing = userRepository.findById(Long.valueOf(userId));
				if (existing.isPresent()) {
					System.out.println("Updating user in DB: " + user);
				} else {
					System.out.println("Creating new user in DB: " + user);
				}

				userRepository.save(user);
				redisTemplate.delete("USER_" + userId);
			} else {
				System.out.println("No user found in Redis for ID: " + userId);
			}
		}
	}

}
