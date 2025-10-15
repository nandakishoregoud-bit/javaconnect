package com.javaConnect.web.service;

import java.util.List;
import java.util.UUID;

import com.javaConnect.web.dto.AnswerDTO;
import com.javaConnect.web.dto.FeedBackDTO;
import com.javaConnect.web.dto.FlagDTO;
import com.javaConnect.web.dto.QuestionDTO;
import com.javaConnect.web.entity.Flag;

public interface FlagService {

	String flag(UUID userId, FlagDTO flagDto) throws IllegalAccessException;

	List<QuestionDTO> getFlagedQuestions();

	List<AnswerDTO> getFlagedAnswers();

	List<FeedBackDTO> getFlagedFeedBacks();

	String unflag(UUID userId, UUID flagId) throws IllegalAccessException;

	Flag checkIfFlagged(UUID userId, UUID questionId, UUID answerId, UUID feedbackId) throws IllegalAccessException;


	
}
