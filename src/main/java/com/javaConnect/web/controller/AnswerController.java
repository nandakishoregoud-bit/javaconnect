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

import com.javaConnect.web.dto.AnswerDTO;
import com.javaConnect.web.dto.ApiResponse;
import com.javaConnect.web.service.AnswerService;

@RestController
@RequestMapping("/answers")
public class AnswerController {

	@Autowired
	private AnswerService answerService;
	
	@PostMapping("/post/answer/{userId}")
	public ResponseEntity<?> postAnswer(@PathVariable UUID userId,@RequestBody AnswerDTO answerDTO){
		
		try {
			String message = answerService.postAnswer(userId,answerDTO);

			return ResponseEntity.ok(new ApiResponse<>(true,message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
	}
	
	@PutMapping("/edit/answer/{userId}")
	public ResponseEntity<?> editAnswer(@PathVariable UUID userId,@RequestBody AnswerDTO answerDTO){
		
		String message;
		try {
			message = answerService.editAnswer(userId,answerDTO);
			
			return ResponseEntity.ok(new ApiResponse<>(true,message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
	}
	
	@DeleteMapping("/delete/user/{userId}/answer/{answerId}")
	public ResponseEntity<?> deleteAnswer(@PathVariable UUID userId,@PathVariable UUID answerId){
		
		String message;
		try {
			message = answerService.deleteAnswer(userId,answerId);
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
		return ResponseEntity.ok(new ApiResponse<>(true, message,null));
	}
	
	@GetMapping("/all/answers/question/{questionId}")
	public ResponseEntity<?> getAllAnsForQuestion(@PathVariable UUID questionId){
		
		List<AnswerDTO> answerDTOs = answerService.getAllAns(questionId);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "fetched successfully",answerDTOs));
		
	}
	
}
