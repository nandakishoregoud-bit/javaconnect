package com.javaConnect.web.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaConnect.web.dto.AnswerDTO;
import com.javaConnect.web.entity.Answer;
import com.javaConnect.web.entity.Question;
import com.javaConnect.web.entity.User;
import com.javaConnect.web.repository.AnswerRepository;
import com.javaConnect.web.repository.QuestionRepository;
import com.javaConnect.web.repository.UserRepository;
import com.javaConnect.web.service.AnswerService;


@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

	@Autowired
    private AnswerRepository answerRepo;
    @Autowired
	private UserRepository userRepo;
    @Autowired
    private QuestionRepository questionRepo;

    @Override
    public String postAnswer(UUID userId, AnswerDTO answerDTO) throws IllegalAccessException {
        User user = findUserOrThrow(userId);

        if (user.isBlocked()) {
            throw new IllegalAccessException("Your account has been blocked. You cannot post an answer.");
        }

        if (!userId.toString().equals(answerDTO.getAnsweredBy())) {
            return "User ID mismatch — cannot post answer.";
        }

        Question question = findQuestionOrThrow(UUID.fromString(answerDTO.getQuestionId()));

        Answer answer = new Answer();
                answer.setAuther(user);
                answer.setQuestion(question);
                answer.setContent(answerDTO.getContent());
                answer.setCreatedAt(LocalDateTime.now());

        answerRepo.save(answer);
        return "Answer posted successfully.";
    }

    @Override
    public String editAnswer(UUID userId, AnswerDTO answerDTO) throws IllegalAccessException {
        User user = findUserOrThrow(userId);
        Answer answer = findAnswerOrThrow(UUID.fromString(answerDTO.getId()));

        if (!answer.getAuther().getId().equals(userId)) {
            return "You are not the owner of this answer.";
        }

        Question question = findQuestionOrThrow(UUID.fromString(answerDTO.getQuestionId()));

        answer.setQuestion(question);
        answer.setContent(answerDTO.getContent());
        answer.setCreatedAt(LocalDateTime.now());

        answerRepo.save(answer);
        return "Answer updated successfully.";
    }

    @Override
    public List<AnswerDTO> getAllAns(UUID questionId) {
        return answerRepo.findByQuestionId(questionId)
                .stream()
                .map(a ->{
                	AnswerDTO adto = new AnswerDTO();
        			adto.setAnsweredBy(a.getAuther().getId().toString());
        			adto.setId(a.getId().toString());
        			adto.setContent(a.getContent());
        			adto.setQuestionId(a.getQuestion().getId().toString());
        			adto.setCreatedAt(a.getCreatedAt());
        			adto.setUpdatedAt(a.getUpdatedAt());
        			return adto;
                })
                .toList();
    }

    @Override
    public String deleteAnswer(UUID userId, UUID answerId) throws IllegalAccessException {
        User user = findUserOrThrow(userId);
        Answer answer = findAnswerOrThrow(answerId);

        if (!answer.getAuther().getId().equals(userId)) {
            return "You are not the owner of this answer.";
        }

        answerRepo.delete(answer);
        return "Answer deleted successfully.";
    }

    // ---------- Helper Methods ----------
    private User findUserOrThrow(UUID userId) throws IllegalAccessException {
        return userRepo.findById(userId)
                .orElseThrow(() -> new IllegalAccessException("User not found with ID: " + userId));
    }

    private Question findQuestionOrThrow(UUID questionId) throws IllegalAccessException {
        return questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalAccessException("Question not found with ID: " + questionId));
    }

    private Answer findAnswerOrThrow(UUID answerId) throws IllegalAccessException {
        return answerRepo.findById(answerId)
                .orElseThrow(() -> new IllegalAccessException("Answer not found with ID: " + answerId));
    }
}
