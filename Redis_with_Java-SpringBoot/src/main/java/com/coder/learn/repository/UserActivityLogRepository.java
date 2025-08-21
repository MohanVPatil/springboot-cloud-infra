package com.coder.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coder.learn.entity.UserActivityLog;

@Repository
public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
	UserActivityLog findTopByUserIdOrderByTimestampDesc(Long userId);
}
