package com.javaConnect.web.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaConnect.web.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {


	List<Answer> findByQuestionId(UUID questionId);

	int countByQuestionId(UUID id);


}
