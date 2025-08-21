package com.coder.learn.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserAccess {
	@Id
	private Long userId;
	private String accessType;

	@ElementCollection
	@CollectionTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "permission")
	private List<String> permissions;
}
