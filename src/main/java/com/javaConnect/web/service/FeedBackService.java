package com.javaConnect.web.service;

import java.util.List;
import java.util.UUID;

import com.javaConnect.web.dto.FeedBackDTO;

public interface FeedBackService {

	String saveFeedBack(UUID userId, FeedBackDTO feedBackDTO) throws IllegalAccessException;

	String editFeedBack(UUID userId, FeedBackDTO feedBackDTO) throws IllegalAccessException;

	List<FeedBackDTO> getAll();

	String deleteFeedBack(UUID userId, UUID feedbackId) throws IllegalAccessException;

}
