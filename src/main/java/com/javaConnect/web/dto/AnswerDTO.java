package com.javaConnect.web.dto;

import java.time.LocalDateTime;
import java.util.List;


public class AnswerDTO {

	private String id;
	
	private String questionId;
	
	private String content;
	
	private String answeredBy;
	
	private String answerByName;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private List<FlagDTO> flagDTOs;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnsweredBy() {
		return answeredBy;
	}

	public void setAnsweredBy(String answeredBy) {
		this.answeredBy = answeredBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAnswerByName() {
		return answerByName;
	}

	public void setAnswerByName(String answerByName) {
		this.answerByName = answerByName;
	}
	
	
}
