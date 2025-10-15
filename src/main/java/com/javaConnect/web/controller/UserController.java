package com.javaConnect.web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaConnect.web.dto.ApiResponse;
import com.javaConnect.web.dto.UserDTO;
import com.javaConnect.web.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile/{userId}")
	public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable UUID userId){
		
		UserDTO dto;
		try {
			dto = userService.getUser(userId);
			return ResponseEntity.ok(new ApiResponse<>(true,"fetched user successfully",dto));
		} catch (IllegalAccessException e) {
			return ResponseEntity.badRequest().body(
	                new ApiResponse<>(false, e.getMessage(), null));
		}
	}
	
	@PutMapping("/update/profile/{userId}")
	public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable UUID userId,@RequestBody UserDTO userDTO){
		
		UserDTO dto;
		try {
			dto = userService.updateUser(userId,userDTO);
			return ResponseEntity.ok(new ApiResponse<>(true,"updated user successfully",dto));
		} catch (IllegalAccessException e) {
			return ResponseEntity.badRequest().body(
	                new ApiResponse<>(false, e.getMessage(), null));
		}
	}
}
