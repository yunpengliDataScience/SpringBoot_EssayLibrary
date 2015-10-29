package com.library.essay.services;

import java.util.List;

import com.library.essay.persistence.entities.User;

public interface UserService {

	User getUser(String userName);

	List<User> getUsers();

	User saveOrUpdate(User user);

	void delete(User user);

	void deleteAll();
}
