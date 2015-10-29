package com.library.essay.security.beans;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;

import com.library.essay.persistence.entities.User;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private User user;

	public CurrentUser(User user) {
		super(user.getUserName(), user.getPassword(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.join(user.getRoles(), ',')));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return user.getId();
	}

}
