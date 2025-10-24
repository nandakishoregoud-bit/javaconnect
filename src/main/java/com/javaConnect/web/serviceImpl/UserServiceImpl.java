package com.javaConnect.web.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaConnect.web.config.JwtUtil;
import com.javaConnect.web.dto.LoginRequest;
import com.javaConnect.web.dto.UserDTO;
import com.javaConnect.web.entity.User;
import com.javaConnect.web.repository.UserRepository;
import com.javaConnect.web.service.EmailService;
import com.javaConnect.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private JwtUtil jwtUtil;
	@Override
	public UserDTO register(User user) throws IllegalAccessException {
		
		boolean ispresent = userRepo.existsByEmail(user.getEmail());
		
		if(ispresent == true) {
			return null;
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		boolean isSuccess = emailService.sendEmail(user);
		
		if (!isSuccess) {
		    throw new IllegalArgumentException("Failed to send email. Please enter a valid email address.");
		}
		User saveduser = userRepo.save(user);
		UserDTO dto = new UserDTO();
		dto.setId(saveduser.getId().toString());
		dto.setEmail(saveduser.getEmail());
		dto.setUserName(saveduser.getUserName());
		dto.setBlocked(saveduser.isBlocked());
		
		return dto;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		
		List<User> users = userRepo.findAll();
		
		return users.stream()
				.map(u ->{
					
					UserDTO userDTO = new UserDTO();
					userDTO.setId(u.getId().toString());
					userDTO.setUserName(u.getUserName());
					userDTO.setEmail(u.getEmail());
					userDTO.setBlocked(u.isBlocked());
					return userDTO;
				}).toList();
	}

	@Override
	public String blockUser(UUID userId) throws IllegalAccessException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new IllegalAccessException("User not found with id"));
		user.setBlocked(true);
		userRepo.save(user);
		
		return "blocked successfully";
	}
	
	@Override
	public String unBlockUser(UUID userId) throws IllegalAccessException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new IllegalAccessException("User not found with id"));
		user.setBlocked(false);
		userRepo.save(user);
		
		return "unblocked successfully";
	}

	@Override
	public UserDTO login(LoginRequest loginRequest) throws IllegalAccessException {

	    User user = userRepo.findByEmail(loginRequest.getEmail());
	    if (user == null) {
	        throw new IllegalAccessException("Email not found");
	    }

	    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
	        throw new IllegalAccessException("Invalid password");
	    }

	    String token = jwtUtil.generateToken(user.getId().toString());

	    UserDTO dto = new UserDTO();
	    dto.setId(user.getId().toString());
	    dto.setEmail(user.getEmail());
	    dto.setUserName(user.getUserName());
	    dto.setBlocked(user.isBlocked());
	    dto.setToken(token);

	    return dto;
	}


	@Override
	public UserDTO getUser(UUID userId) throws IllegalAccessException {
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new IllegalAccessException("User not found with id"));
		
		UserDTO dto = new UserDTO();
		dto.setId(user.getId().toString());
		dto.setUserName(user.getUserName());
		dto.setEmail(user.getEmail());
		dto.setBlocked(user.isBlocked());
		
		return dto;
	}

	@Override
	public UserDTO updateUser(UUID userId, UserDTO userDTO) throws IllegalAccessException {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new IllegalAccessException("User not found with id"));
		user.setUserName(userDTO.getUserName());
		user.setEmail(userDTO.getEmail());
		
		User u = userRepo.save(user);
		
		UserDTO dto = new UserDTO();
		dto.setId(u.getId().toString());
		dto.setUserName(u.getUserName());
		dto.setEmail(u.getEmail());
		dto.setBlocked(u.isBlocked());
		
		return dto;
	}

	@Override
	public String changepassword(String email, String newPassword) throws IllegalAccessException {
		
		User user = userRepo.findByEmail(email);
		if(user == null) {
				throw new IllegalAccessException("User not found with email");
		}
		user.setPassword(newPassword);
		
		userRepo.save(user);
		
		return "password changed successfully";
	}
	
}
