package com.coder.learn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserActivityLog {

	@Id
	private Long userId;
	private String action;
	private String timestamp;
}
