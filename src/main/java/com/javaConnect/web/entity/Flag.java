package com.javaConnect.web.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="flag")
public class Flag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String flagedRession;
	
	private String flagedById;
	
	private String flagedOnQuestionId;
	
	private String flagedOnAnswerId;
	
	private String flagedOnFeedBackId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFlagedRession() {
		return flagedRession;
	}

	public void setFlagedRession(String flagedRession) {
		this.flagedRession = flagedRession;
	}

	public String getFlagedById() {
		return flagedById;
	}

	public void setFlagedById(String flagedById) {
		this.flagedById = flagedById;
	}


	public String getFlagedOnQuestionId() {
		return flagedOnQuestionId;
	}

	public void setFlagedOnQuestionId(String flagedOnQuestionId) {
		this.flagedOnQuestionId = flagedOnQuestionId;
	}

	public String getFlagedOnAnswerId() {
		return flagedOnAnswerId;
	}

	public void setFlagedOnAnswerId(String flagedOnAnswerId) {
		this.flagedOnAnswerId = flagedOnAnswerId;
	}

	public String getFlagedOnFeedBackId() {
		return flagedOnFeedBackId;
	}

	public void setFlagedOnFeedBackId(String flagedOnFeedBackId) {
		this.flagedOnFeedBackId = flagedOnFeedBackId;
	}
	
}
