package com.javaConnect.web.service;

import java.util.List;
import java.util.UUID;

import com.javaConnect.web.dto.LoginRequest;
import com.javaConnect.web.dto.UserDTO;
import com.javaConnect.web.entity.User;

public interface UserService {

	UserDTO register(User user) throws IllegalAccessException;

	List<UserDTO> getAllUsers();

	String blockUser(UUID userId) throws IllegalAccessException;

	String unBlockUser(UUID userId) throws IllegalAccessException;

	UserDTO login(LoginRequest loginRequest) throws IllegalAccessException;

	UserDTO getUser(UUID userId) throws IllegalAccessException;

	UserDTO updateUser(UUID userId, UserDTO userDTO) throws IllegalAccessException;

	String changepassword(String email, String newPassword) throws IllegalAccessException;

	
}
