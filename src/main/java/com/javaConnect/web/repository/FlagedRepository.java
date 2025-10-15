package com.javaConnect.web.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaConnect.web.entity.Flag;

public interface FlagedRepository extends JpaRepository<Flag, UUID> {

	List<Flag> findByFlagedOnQuestionIdIsNotNull();

	List<Flag> findByFlagedOnAnswerIdIsNotNull();

	List<Flag> findByFlagedOnFeedBackIdIsNotNull();

}
