package com.javaConnect.web.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaConnect.web.dto.FeedBackDTO;
import com.javaConnect.web.entity.FeedBack;

public interface FeedBackRepository extends JpaRepository<FeedBack, UUID> {

	List<FeedBack> findAllByQuestionId(String questionId);

}
