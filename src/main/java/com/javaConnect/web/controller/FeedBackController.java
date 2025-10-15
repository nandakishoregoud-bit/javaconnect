package com.javaConnect.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaConnect.web.dto.ApiResponse;
import com.javaConnect.web.dto.FeedBackDTO;
import com.javaConnect.web.service.FeedBackService;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {
	
	@Autowired
	private FeedBackService fBService;
	
	@PostMapping("/post/feedback/{userId}")
	public ResponseEntity<?> saveFeedBack(@PathVariable UUID userId,@RequestBody FeedBackDTO feedBackDTO){
		
		String message;
		try {
			message = fBService.saveFeedBack(userId,feedBackDTO);
			return ResponseEntity.ok(new ApiResponse<>(true,message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
	}
	
	@PutMapping("/update/feedback/{userId}")
	public ResponseEntity<?> editFeedBack(@PathVariable UUID userId,@RequestBody FeedBackDTO feedBackDTO){
		
		try {
			String message = fBService.editFeedBack(userId,feedBackDTO);
			return ResponseEntity.ok(new ApiResponse<>(true, message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
	}
	
	@DeleteMapping("/delete/{userId}/{feedbackId}")
	public ResponseEntity<?> deleteFeedBack(@PathVariable UUID userId,@PathVariable UUID feedbackId){
		
		try {
			String message = fBService.deleteFeedBack(userId,feedbackId);
			return ResponseEntity.ok(new ApiResponse<>(true, message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
	}
	
	@GetMapping("/feedbacks")
	public ResponseEntity<?> getall(){
		
		List<FeedBackDTO> dtos = fBService.getAll();
		
		return ResponseEntity.ok(new ApiResponse<>(true, "fetched successfully",dtos));
	}
	

}
