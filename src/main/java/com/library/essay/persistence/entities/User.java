package com.library.essay.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue
	private Long id;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ENABLED")
	private Boolean enabled;

	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<UserRole> roles;

	public User() {
		roles = new ArrayList<UserRole>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public void addRole(UserRole role) {
		this.roles.add(role);

		if (role.getUser() != this) {
			role.setUser(this);
		}
	}

	@Override
	public String toString() {

		StringBuilder roleStrings = new StringBuilder();

		for (UserRole role : roles) {
			roleStrings.append(role.getRole() + ", ");
		}

		return "Id: " + id + ", UserName: " + userName + ", Password: "
				+ password + ", Roles: " + roleStrings.toString();

	}
}
