package com.library.essay.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.essay.persistence.entities.User;
import com.library.essay.security.beans.CurrentUser;

@Service(value = "myUserDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;

	@Override
	public CurrentUser loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		logger.info("======================================================");
		logger.info("myUserDetailsService's loadUserByUsername() is called.");
		logger.info("======================================================");
		
		System.out.println("======================================================");
		System.out.println("myUserDetailsService's loadUserByUsername() is called.");
		System.out.println("======================================================");
		
		User user = userService.getUser(userName);

		CurrentUser currentUser = new CurrentUser(user);

		return currentUser;
	}

}
