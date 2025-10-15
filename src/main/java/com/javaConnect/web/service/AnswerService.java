package com.javaConnect.web.service;

import java.util.List;
import java.util.UUID;

import com.javaConnect.web.dto.AnswerDTO;

public interface AnswerService {

	String postAnswer(UUID userId, AnswerDTO answerDTO) throws IllegalAccessException;

	String editAnswer(UUID userId, AnswerDTO answerDTO) throws IllegalAccessException;

	List<AnswerDTO> getAllAns(UUID questionId);

	String deleteAnswer(UUID userId, UUID answerId) throws IllegalAccessException;

}
