package com.javaConnect.web.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name ="answers")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@ManyToOne // ✅ Correct way
	@JoinColumn(name = "questionId")
	private Question question;
	
	private String content;
	
	@ManyToOne 
	@JoinColumn(name = "autherId")
	private User auther;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@OneToMany
	@JoinColumn(name = "feedBackId")
	private List<FeedBack> feedBack;
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuther() {
		return auther;
	}

	public void setAuther(User auther) {
		this.auther = auther;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	

	public List<FeedBack> getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(List<FeedBack> feedBack) {
		this.feedBack = feedBack;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

		
}
