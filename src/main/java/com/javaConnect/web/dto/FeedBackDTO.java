package com.javaConnect.web.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FeedBackDTO {
	
	private String id;
	
	private String feedback;
	
	private String givenBy;
	
	private String givenByName;
	
	private String questionId;
	
	private String answerId;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private List<FlagDTO> flagDTOs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getGivenBy() {
		return givenBy;
	}

	public void setGivenBy(String givenBy) {
		this.givenBy = givenBy;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public String getGivenByName() {
		return givenByName;
	}

	public void setGivenByName(String givenByName) {
		this.givenByName = givenByName;
	}

	
}
