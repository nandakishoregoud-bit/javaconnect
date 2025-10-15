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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaConnect.web.dto.AnswerDTO;
import com.javaConnect.web.dto.ApiResponse;
import com.javaConnect.web.dto.FeedBackDTO;
import com.javaConnect.web.dto.FlagDTO;
import com.javaConnect.web.dto.QuestionDTO;
import com.javaConnect.web.entity.Flag;
import com.javaConnect.web.service.FlagService;

@RestController
@RequestMapping("/flag")
public class FlagController {

	@Autowired
	private FlagService flagService;
	
	@PostMapping("/{userId}")
	public ResponseEntity<?> flag(@PathVariable UUID userId,@RequestBody FlagDTO flagDto) {
		
		try {
			String message = flagService.flag(userId,flagDto);
			return ResponseEntity.ok(new ApiResponse<>(true, message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
	}
	
	@GetMapping("/check/{userId}")
    public ResponseEntity<?> checkFlag(
            @PathVariable UUID userId,
            @RequestParam(required = false) UUID questionId,
            @RequestParam(required = false) UUID answerId,
            @RequestParam(required = false) UUID feedbackId) {

        try {
            Flag flag = flagService.checkIfFlagged(userId, questionId, answerId, feedbackId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Flag found", flag));
        } catch (IllegalAccessException e) {
            return ResponseEntity.ok(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
	
	@DeleteMapping("/unflag/user/{userId}/{flagId}")
	public ResponseEntity<?> unflag(@PathVariable UUID userId,@PathVariable UUID flagId){
		try {
			String message = flagService.unflag(userId,flagId);
			return ResponseEntity.ok(new ApiResponse<>(true,message,null));
		} catch (IllegalAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(),null));
		}
		
	}
	
	@GetMapping("/getFlagedQuestions")
    public ResponseEntity<?> getFlagedQuestions() {
        List<QuestionDTO> dtos = flagService.getFlagedQuestions();

        if (dtos.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false,"No flagged questions found ✅",null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "fetched flaged questions successfully",dtos));
    }
	
	@GetMapping("/getFlagedAnswers")
    public ResponseEntity<?> getFlagedAnswers() {
        List<AnswerDTO> dtos = flagService.getFlagedAnswers();

        if (dtos.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false, "No flagged answers found",null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true,"fetched flaged answers successfully",dtos));
    }
	
	@GetMapping("/getFlagedFeedBacks")
    public ResponseEntity<?> getFlagedFeedBacks() {
        List<FeedBackDTO> dtos = flagService.getFlagedFeedBacks();

        if (dtos.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false,"No flagged feedbacks found ✅",null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "fetched successfully",dtos));
    }
	
}
