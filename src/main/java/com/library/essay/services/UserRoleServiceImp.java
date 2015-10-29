package com.library.essay.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.essay.persistence.entities.UserRole;
import com.library.essay.persistence.repositories.UserRoleRepository;

@Service(value = "userRoleService")
@Transactional
public class UserRoleServiceImp implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<UserRole> getUserRoles() {

		return userRoleRepository.findAll();
	}

	@Override
	public UserRole saveOrUpdate(UserRole userRole) {

		return userRoleRepository.save(userRole);
	}

	@Override
	public void delete(UserRole userRole) {
		userRoleRepository.delete(userRole);
	}

	@Override
	public void deleteAll() {
		userRoleRepository.deleteAll();
	}

}
