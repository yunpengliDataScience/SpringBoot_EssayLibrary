package com.library.essay.persistence.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLE")
public class UserRole implements Serializable {

	@Id
	@Column(name = "USER_ROLE_ID")
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "ROLE")
	private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;

		if (!user.getRoles().contains(this)) {
			user.getRoles().add(this);
		}
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return role;
	}
}
