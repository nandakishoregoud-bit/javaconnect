package com.javaConnect.web.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaConnect.web.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{

	boolean existsByEmail(String email);

	User findByEmail(String email);

}
