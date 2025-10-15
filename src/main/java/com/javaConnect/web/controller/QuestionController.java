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
import com.javaConnect.web.dto.QuestionDTO;
import com.javaConnect.web.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;

	@PostMapping("/post/question/{id}")
	public ResponseEntity<?> postQuestion(@PathVariable UUID id, @RequestBody QuestionDTO questionDTO){
		
		try {
			QuestionDTO savedquestionDTO = questionService.postQuestion(id,questionDTO);
			return ResponseEntity.ok(new ApiResponse<>(true, "Question posted successfully", savedquestionDTO));
		} catch (IllegalAccessException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
	}
	
	@PutMapping("/update/question/{questionId}")
	public ResponseEntity<?> editQuestion(@PathVariable UUID questionId, @RequestBody QuestionDTO questionDTO){
		
		try {
			QuestionDTO updatedquestionDTO = questionService.updateQuestion(questionId,questionDTO);
			if(updatedquestionDTO != null) {
			return ResponseEntity.ok(new ApiResponse<>(true, "Question updated successfully",updatedquestionDTO));
			}
			return ResponseEntity.badRequest().body(new ApiResponse<>(false, "your not the owner of the question",null));
		} catch (IllegalAccessException e) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
		
	}
	
	@DeleteMapping("/delete/question/{questionId}/user/{userId}")
	public ResponseEntity<?> deleteQuestion(@PathVariable UUID questionId, @PathVariable UUID userId){
		
			String message;
			try {
				message = questionService.deleteQuestion(questionId,userId);
			} catch (IllegalAccessException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
			}
			return ResponseEntity.ok(new ApiResponse<>(true,message,null));
		
		
	}
	
	
	@GetMapping("/getall/questions")
	public ResponseEntity<?> getAllQuestions(){
		
		List<QuestionDTO> dtos = questionService.getAllQuestions();
		
		return ResponseEntity.ok(new ApiResponse<>(true, "question fetched successfully",dtos));
	}
	

	@GetMapping("/getquestion/{questionId}")
	public ResponseEntity<?> getById(@PathVariable UUID questionId){
		
		QuestionDTO dto;
		try {
			dto = questionService.getById(questionId);
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
		return ResponseEntity.ok(new ApiResponse<>(true, "fetched successfully",dto));
	}
	
	@GetMapping("/getquestion/user/{userId}")
	public ResponseEntity<?> getAllQuestionsByUserId(@PathVariable UUID userId){
		
		List<QuestionDTO> dtos = questionService.getAllQuestionsByUserId(userId);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "fetched successfully",dtos));
	}
}
