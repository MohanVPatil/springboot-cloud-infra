package com.coder.learn.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	// private Long userId;
	private String action;
	private String timestamp;

}