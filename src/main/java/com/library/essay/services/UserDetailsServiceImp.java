package com.library.essay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.essay.persistence.entities.User;
import com.library.essay.security.beans.CurrentUser;

@Service(value = "userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public CurrentUser loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.getUser(userName);

		CurrentUser currentUser = new CurrentUser(user);

		return currentUser;
	}

}
