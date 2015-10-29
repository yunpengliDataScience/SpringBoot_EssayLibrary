package com.library.essay.services;

import java.util.List;

import com.library.essay.persistence.entities.UserRole;

public interface UserRoleService {

	List<UserRole> getUserRoles();

	UserRole saveOrUpdate(UserRole userRole);

	void delete(UserRole userRole);

	void deleteAll();
}
