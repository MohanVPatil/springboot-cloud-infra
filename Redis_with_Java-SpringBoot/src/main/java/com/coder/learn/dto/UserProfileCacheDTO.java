package com.coder.learn.dto;

import com.coder.learn.entity.User;
import com.coder.learn.entity.UserAccess;
import com.coder.learn.entity.UserActivityLog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileCacheDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;
	private UserAccess userAccess;
	private UserActivityLog userActivityLog;

}
