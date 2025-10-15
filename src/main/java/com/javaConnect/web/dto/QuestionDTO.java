package com.javaConnect.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.javaConnect.web.entity.Flag;


public class QuestionDTO {
	
	private String id;
	
	private String title;
	
	private String description;
	
	private String difficulty;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private String userId;
	
	private List<AnswerDTO> answerDto;
	
	private List<FeedBackDTO> feedbacks;
	
	private List<FlagDTO> flagDTOs;
	
	private int answerCount;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public List<AnswerDTO> getAnswerDto() {
		return answerDto;
	}

	public void setAnswerDto(List<AnswerDTO> answerDto) {
		this.answerDto = answerDto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<FeedBackDTO> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<FeedBackDTO> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public List<FlagDTO> getFlagDTOs() {
		return flagDTOs;
	}

	public void setFlagDTOs(List<FlagDTO> flagDTOs) {
		this.flagDTOs = flagDTOs;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	

	
}
