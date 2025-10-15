package com.javaConnect.web.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaConnect.web.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

	List<Question> findAllByAutherId(UUID userId);

}
