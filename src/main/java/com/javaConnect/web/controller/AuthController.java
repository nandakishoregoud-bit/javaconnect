package com.javaConnect.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaConnect.web.dto.ApiResponse;
import com.javaConnect.web.dto.LoginRequest;
import com.javaConnect.web.dto.UserDTO;
import com.javaConnect.web.entity.User;
import com.javaConnect.web.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody User userDTO) {
        try {
    	UserDTO savedUser = userService.register(userDTO);
    	if(savedUser !=null) {
        return ResponseEntity.ok(
            new ApiResponse<>(true, "User registered successfully", savedUser));
    	}else {
    		
    	        return ResponseEntity.ok(
    	            new ApiResponse<>(true, "User with this email already Present", savedUser));
    	}
        }catch (Exception e) {
        	 return ResponseEntity.badRequest().body(
                     new ApiResponse<>(false, e.getMessage(), null));
		}
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserDTO dto = userService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", dto));
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/allusers")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "All users fetched", users));
    }
    
    @PutMapping("/changepassword")
    public ResponseEntity<ApiResponse<UserDTO>> changepassword(@RequestParam String email,@RequestParam String newPassword){
    	try {
            String message = userService.changepassword(email,newPassword);
            return ResponseEntity.ok(new ApiResponse<>(true, message, null));
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
}
