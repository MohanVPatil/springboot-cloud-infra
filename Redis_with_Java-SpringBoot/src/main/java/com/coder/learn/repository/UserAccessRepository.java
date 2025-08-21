package com.coder.learn.repository;

import com.coder.learn.entity.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Long> {
	UserAccess findByUserId(Long userId);
}
