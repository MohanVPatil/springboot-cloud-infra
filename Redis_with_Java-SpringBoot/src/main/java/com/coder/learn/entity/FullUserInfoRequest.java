package com.coder.learn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FullUserInfoRequest {
	private User user;
	private UserAccess userAccess;
	private UserActivityLog userActivityLog;
}
