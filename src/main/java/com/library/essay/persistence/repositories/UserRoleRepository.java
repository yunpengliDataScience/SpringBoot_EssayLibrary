package com.library.essay.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.essay.persistence.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
