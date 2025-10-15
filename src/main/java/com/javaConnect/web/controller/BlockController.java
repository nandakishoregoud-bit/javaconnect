package com.javaConnect.web.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaConnect.web.dto.ApiResponse;
import com.javaConnect.web.service.UserService;

@RestController
@RequestMapping("/block")
public class BlockController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/{userId}")
	public ResponseEntity<?> blockUser(@PathVariable UUID userId){
		
		String message;
		try {
			message = userService.blockUser(userId);
			return ResponseEntity.ok(new ApiResponse<>(true, message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
	}
	
	@PutMapping("/unblock/{userId}")
	public ResponseEntity<?> unBlockUser(@PathVariable UUID userId){
		
		String message;
		try {
			message = userService.unBlockUser(userId);
			return ResponseEntity.ok(new ApiResponse<>(true, message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
	}
	
}
