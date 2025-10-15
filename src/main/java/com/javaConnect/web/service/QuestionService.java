package com.javaConnect.web.service;

import java.util.List;
import java.util.UUID;

import com.javaConnect.web.dto.QuestionDTO;

public interface QuestionService {

	QuestionDTO postQuestion(UUID id, QuestionDTO questionDTO) throws IllegalAccessException;

	QuestionDTO updateQuestion(UUID questionId, QuestionDTO questionDTO) throws IllegalAccessException;

	List<QuestionDTO> getAllQuestions();


	String deleteQuestion(UUID questionId, UUID userId) throws IllegalAccessException;

	QuestionDTO getById(UUID questionId) throws IllegalAccessException;

	List<QuestionDTO> getAllQuestionsByUserId(UUID userId);


}
