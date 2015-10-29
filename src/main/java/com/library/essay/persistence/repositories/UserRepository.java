package com.library.essay.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.essay.persistence.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);

}
