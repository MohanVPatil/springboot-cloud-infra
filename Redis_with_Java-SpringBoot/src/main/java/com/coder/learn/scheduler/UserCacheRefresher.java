package com.coder.learn.scheduler;

import com.coder.learn.entity.User;
import com.coder.learn.entity.UserAccess;
import com.coder.learn.entity.UserActivityLog;
import com.coder.learn.service.UserCacheService;
import com.coder.learn.repository.UserRepository;
import com.coder.learn.repository.UserAccessRepository;
import com.coder.learn.repository.UserActivityLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserCacheRefresher {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAccessRepository userAccessRepository;

	@Autowired
	private UserActivityLogRepository userActivityLogRepository;

	@Autowired
	private UserCacheService userCacheService;

	// Run every 5 minutes
	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void refreshImportantUsersCache() {
		List<Long> importantUserIds = Arrays.asList(1L, 2L, 3L);

		for (Long userId : importantUserIds) {
			User user = userRepository.findById(userId).orElse(null);
			UserAccess access = userAccessRepository.findByUserId(userId);
			UserActivityLog activityLog = userActivityLogRepository.findTopByUserIdOrderByTimestampDesc(userId);

			if (user != null) {
				userCacheService.cacheUserDetails(user, access, activityLog);
			}
		}
	}
}
