package com.javaConnect.web.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javaConnect.web.dto.*;
import com.javaConnect.web.entity.*;
import com.javaConnect.web.repository.*;
import com.javaConnect.web.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired 
    private UserRepository userRepo;
    @Autowired 
    private QuestionRepository questionRepo;
    @Autowired 
    private AnswerRepository answerRepo;
    @Autowired 
    private FeedBackRepository feedBackRepo;

    @Override
    public QuestionDTO postQuestion(UUID userId, QuestionDTO dto) throws IllegalAccessException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalAccessException("User not found with ID: " + userId));

        if (user.isBlocked()) 
            throw new IllegalAccessException("Your account is blocked — you cannot post questions");

        Question question = new Question();
        question.setTitle(dto.getTitle());
        question.setDescription(dto.getDescription());
        question.setDifficulty(dto.getDifficulty());
        question.setCreatedAt(LocalDateTime.now());
        question.setAuther(user);

        return toDTO(questionRepo.save(question));
    }

    @Override
    public QuestionDTO updateQuestion(UUID questionId, QuestionDTO dto) throws IllegalAccessException {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalAccessException("No question found with ID: " + questionId));

        // Ownership check
        if (!question.getAuther().getId().toString().equals(dto.getUserId()))
            throw new IllegalAccessException("You are not the owner of this question");

        question.setTitle(dto.getTitle());
        question.setDescription(dto.getDescription());
        question.setDifficulty(dto.getDifficulty());
        question.setUpdatedAt(LocalDateTime.now());

        return toDTO(questionRepo.save(question));
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        return questionRepo.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public String deleteQuestion(UUID questionId, UUID userId) throws IllegalAccessException {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalAccessException("Question not found with ID: " + questionId));

        if (!question.getAuther().getId().equals(userId))
            return "You are not authorized to delete this question";

        questionRepo.delete(question);
        return "Question deleted successfully ✅";
    }

    @Override
    public QuestionDTO getById(UUID questionId) throws IllegalAccessException {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new IllegalAccessException("No question found with ID: " + questionId));

        QuestionDTO dto = toDTO(question);
        dto.setAnswerDto(getAnswers(questionId));
        dto.setFeedbacks(getFeedbacks(questionId));
        return dto;
    }

    @Override
    public List<QuestionDTO> getAllQuestionsByUserId(UUID userId) {
        return questionRepo.findAllByAutherId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // -------------------------- 🔹 Private Helpers -------------------------- //

    private QuestionDTO toDTO(Question q) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(q.getId().toString());
        dto.setUserId(q.getAuther().getId().toString());
        dto.setTitle(q.getTitle());
        dto.setDescription(q.getDescription());
        dto.setDifficulty(q.getDifficulty());
        dto.setCreatedAt(q.getCreatedAt());
        dto.setUpdatedAt(q.getUpdatedAt());
        dto.setAnswerCount(answerRepo.countByQuestionId(q.getId()));

        
        return dto;
    }

    private List<AnswerDTO> getAnswers(UUID questionId) {
        return answerRepo.findByQuestionId(questionId)
                .stream()
                .map(a ->{
                	
                	AnswerDTO adto = new AnswerDTO();
        			adto.setAnsweredBy(a.getAuther().getId().toString());
        			adto.setAnswerByName(a.getAuther().getUserName());
        			adto.setId(a.getId().toString());
        			adto.setContent(a.getContent());
        			adto.setQuestionId(a.getQuestion().getId().toString());
        			adto.setCreatedAt(a.getCreatedAt());
        			adto.setUpdatedAt(a.getUpdatedAt());
        			return adto;
                })
                .toList();
    }

    private List<FeedBackDTO> getFeedbacks(UUID questionId) {
        return feedBackRepo.findAllByQuestionId(questionId.toString())
                .stream()
                .map(fb -> {
                	
                	FeedBackDTO fdto = new FeedBackDTO();
        			fdto.setId(fb.getId().toString());
        			fdto.setQuestionId(fb.getQuestionId());
        			fdto.setAnswerId(fb.getAnswerId());
        			fdto.setGivenBy(fb.getGivenBy().getId().toString());
        			fdto.setGivenByName(fb.getGivenBy().getUserName());
        			fdto.setFeedback(fb.getFeedback());
        			fdto.setCreatedAt(fb.getCreatedAt());
        			fdto.setUpdatedAt(fb.getUpdatedAt());
        			return fdto;
                })
                .toList();
    }
}
