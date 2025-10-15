package com.javaConnect.web.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaConnect.web.dto.FeedBackDTO;
import com.javaConnect.web.entity.Answer;
import com.javaConnect.web.entity.FeedBack;
import com.javaConnect.web.entity.Question;
import com.javaConnect.web.entity.User;
import com.javaConnect.web.repository.AnswerRepository;
import com.javaConnect.web.repository.FeedBackRepository;
import com.javaConnect.web.repository.QuestionRepository;
import com.javaConnect.web.repository.UserRepository;
import com.javaConnect.web.service.FeedBackService;


@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

	@Autowired
    private FeedBackRepository feedbackRepo;
	@Autowired
    private UserRepository userRepo;
	@Autowired
    private QuestionRepository questionRepo;
	@Autowired
    private AnswerRepository answerRepo;

    @Override
    public String saveFeedBack(UUID userId, FeedBackDTO dto) throws IllegalAccessException {
        User user = findUser(userId);

        if (user.isBlocked()) {
            throw new IllegalAccessException("Your account has been blocked. You can't post feedback.");
        }

        if (!userId.toString().equals(dto.getGivenBy())) {
            throw new IllegalAccessException("User ID mismatch — feedback owner does not match the logged-in user.");
        }

        // Ensure question & answer exist
        if(dto.getQuestionId() == null) {
        	throw new IllegalAccessException("Pls provide a vaild questionId");
        }
        if(dto.getAnswerId() == null) {
        	throw new IllegalAccessException("pls provide a vaild answerId");
        }
        findQuestion(UUID.fromString(dto.getQuestionId()));
        findAnswer(UUID.fromString(dto.getAnswerId()));

        FeedBack feedback = new FeedBack();
                feedback.setQuestionId(dto.getQuestionId());
                feedback.setAnswerId(dto.getAnswerId());
                feedback.setGivenBy(user);
                feedback.setFeedback(dto.getFeedback());
                feedback.setCreatedAt(LocalDateTime.now());
                

        feedbackRepo.save(feedback);
        return "Feedback saved successfully.";
    }

    @Override
    public String editFeedBack(UUID userId, FeedBackDTO dto) throws IllegalAccessException {
        FeedBack existing = feedbackRepo.findById(UUID.fromString(dto.getId()))
                .orElseThrow(() -> new IllegalAccessException("No feedback found with ID: " + dto.getId()));

        if (!userId.equals(existing.getGivenBy().getId())) {
            throw new IllegalAccessException("You are not the owner of this feedback.");
        }

        findUser(userId); // Validate user
        findQuestion(UUID.fromString(dto.getQuestionId()));
        findAnswer(UUID.fromString(dto.getAnswerId()));

        existing.setQuestionId(dto.getQuestionId());
        existing.setAnswerId(dto.getAnswerId());
        existing.setFeedback(dto.getFeedback());
        existing.setUpdatedAt(LocalDateTime.now());

        feedbackRepo.save(existing);
        return "Feedback updated successfully.";
    }

    @Override
    public List<FeedBackDTO> getAll() {
        return feedbackRepo.findAll().stream()
        		.map(fb -> {
                	
                	FeedBackDTO fdto = new FeedBackDTO();
        			fdto.setId(fb.getId().toString());
        			fdto.setQuestionId(fb.getQuestionId());
        			fdto.setAnswerId(fb.getAnswerId());
        			fdto.setGivenBy(fb.getGivenBy().getId().toString());
        			fdto.setFeedback(fb.getFeedback());
        			fdto.setCreatedAt(fb.getCreatedAt());
        			fdto.setUpdatedAt(fb.getUpdatedAt());
        			return fdto;
                })
                .toList();
    }

    @Override
    public String deleteFeedBack(UUID userId, UUID feedbackId) throws IllegalAccessException {
        FeedBack feedback = feedbackRepo.findById(feedbackId)
                .orElseThrow(() -> new IllegalAccessException("No feedback found with ID: " + feedbackId));

        if (!userId.equals(feedback.getGivenBy().getId())) {
            throw new IllegalAccessException("You are not the owner of this feedback.");
        }

        feedbackRepo.delete(feedback);
        return "Feedback deleted successfully.";
    }

    // ---------- Helper Methods ----------
    private User findUser(UUID id) throws IllegalAccessException {
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalAccessException("User not found with ID: " + id));
    }

    private Question findQuestion(UUID id) throws IllegalAccessException {
        return questionRepo.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Question not found with ID: " + id));
    }

    private Answer findAnswer(UUID id) throws IllegalAccessException {
        return answerRepo.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Answer not found with ID: " + id));
    }
}
