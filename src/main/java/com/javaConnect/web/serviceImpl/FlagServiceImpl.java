package com.javaConnect.web.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaConnect.web.dto.AnswerDTO;
import com.javaConnect.web.dto.FeedBackDTO;
import com.javaConnect.web.dto.FlagDTO;
import com.javaConnect.web.dto.QuestionDTO;
import com.javaConnect.web.entity.Answer;
import com.javaConnect.web.entity.FeedBack;
import com.javaConnect.web.entity.Flag;
import com.javaConnect.web.entity.Question;
import com.javaConnect.web.entity.User;
import com.javaConnect.web.repository.*;
import com.javaConnect.web.service.FlagService;


@Service
@Transactional
public class FlagServiceImpl implements FlagService {

	@Autowired
    private UserRepository userRepo;
	@Autowired
    private QuestionRepository questionRepo;
	@Autowired
    private AnswerRepository answerRepo;
	@Autowired
    private FeedBackRepository feedBackRepo;
	@Autowired
    private FlagedRepository flagRepo;

    @Override
    public String flag(UUID userId, FlagDTO dto) throws IllegalAccessException {
        validateUserOwnership(userId, dto.getFlagedById());

        User user = findUser(userId);
        if (user.isBlocked()) {
            throw new IllegalAccessException("Your account has been blocked — cannot flag content.");
        }

        Flag flag = new Flag();
                flag.setFlagedById(dto.getFlagedById());
                flag.setFlagedRession(dto.getFlagedRession());
                flag.setFlagedOnQuestionId(dto.getFlagedOnQuestionId());
                flag.setFlagedOnAnswerId(dto.getFlagedOnAnswerId());
                flag.setFlagedOnFeedBackId(dto.getFlagedOnFeedBackId());
                

        // Validate entity existence based on flag type
        if (dto.getFlagedOnQuestionId() != null) findQuestion(UUID.fromString(dto.getFlagedOnQuestionId()));
        if (dto.getFlagedOnAnswerId() != null) findAnswer(UUID.fromString(dto.getFlagedOnAnswerId()));
        if (dto.getFlagedOnFeedBackId() != null) findFeedback(UUID.fromString(dto.getFlagedOnFeedBackId()));

        flagRepo.save(flag);
        return "Content flagged successfully.";
    }
    
    @Override
    public Flag checkIfFlagged(UUID userId, UUID questionId, UUID answerId, UUID feedbackId) throws IllegalAccessException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalAccessException("User not found"));

        return flagRepo.findAll().stream()
                .filter(f -> f.getFlagedById().equals(userId.toString()) &&
                        ((questionId != null && questionId.toString().equals(f.getFlagedOnQuestionId())) ||
                        (answerId != null && answerId.toString().equals(f.getFlagedOnAnswerId())) ||
                        (feedbackId != null && feedbackId.toString().equals(f.getFlagedOnFeedBackId()))))
                .findFirst()
                .orElseThrow(() -> new IllegalAccessException("User has not flagged this content"));
    }

    @Override
    public List<QuestionDTO> getFlagedQuestions() {
        return flagRepo.findByFlagedOnQuestionIdIsNotNull().stream()
                .map(flag -> mapFlagToQuestionDTO(flag))
                .filter(dto -> dto != null)
                .toList();
    }

    @Override
    public List<AnswerDTO> getFlagedAnswers() {
        return flagRepo.findByFlagedOnAnswerIdIsNotNull().stream()
                .map(flag -> mapFlagToAnswerDTO(flag))
                .filter(dto -> dto != null)
                .toList();
    }

    @Override
    public List<FeedBackDTO> getFlagedFeedBacks() {
        return flagRepo.findByFlagedOnFeedBackIdIsNotNull().stream()
                .map(flag -> mapFlagToFeedbackDTO(flag))
                .filter(dto -> dto != null)
                .toList();
    }

    @Override
    public String unflag(UUID userId, UUID flagId) throws IllegalAccessException {
        User user = findUser(userId);
        Flag flag = flagRepo.findById(flagId)
                .orElseThrow(() -> new IllegalAccessException("Flag not found with id: " + flagId));

        if (!userId.toString().equals(flag.getFlagedById())) {
            throw new IllegalAccessException("You are not the owner of this flag.");
        }

        flagRepo.delete(flag);
        return "Unflagged successfully.";
    }

    // ----------------- Private Helpers -----------------

    private void validateUserOwnership(UUID userId, String givenById) throws IllegalAccessException {
        if (!userId.toString().equals(givenById)) {
            throw new IllegalAccessException("User ID mismatch — flagedById does not match logged-in user.");
        }
    }

    private User findUser(UUID id) throws IllegalAccessException {
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalAccessException("User not found with id: " + id));
    }

    private Question findQuestion(UUID id) throws IllegalAccessException {
        return questionRepo.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Question not found with id: " + id));
    }

    private Answer findAnswer(UUID id) throws IllegalAccessException {
        return answerRepo.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Answer not found with id: " + id));
    }

    private FeedBack findFeedback(UUID id) throws IllegalAccessException {
        return feedBackRepo.findById(id)
                .orElseThrow(() -> new IllegalAccessException("Feedback not found with id: " + id));
    }

    // ----------------- DTO Mappers -----------------

    private QuestionDTO mapFlagToQuestionDTO(Flag flag) {
        return questionRepo.findById(UUID.fromString(flag.getFlagedOnQuestionId()))
                .map(q -> {
                QuestionDTO dto = new QuestionDTO();
                        dto.setId(q.getId().toString());
                        dto.setTitle(q.getTitle());
                        dto.setDescription(q.getDescription());
                        dto.setDifficulty(q.getDifficulty());
                        dto.setUserId(q.getAuther().getId().toString());
                        dto.setCreatedAt(q.getCreatedAt());
                        dto.setFlagDTOs(List.of(mapFlagToDTO(flag)));
                        return dto;
                })
                .orElse(null);
    }

    private AnswerDTO mapFlagToAnswerDTO(Flag flag) {
        return answerRepo.findById(UUID.fromString(flag.getFlagedOnAnswerId()))
                .map(a -> {
                	AnswerDTO dto = new AnswerDTO();
                        dto.setId(a.getId().toString());
                        dto.setQuestionId(a.getQuestion().getId().toString());
                        dto.setAnsweredBy(a.getAuther().getId().toString());
                        dto.setContent(a.getContent());
                        dto.setCreatedAt(a.getCreatedAt());
                        dto.setFlagDTOs(List.of(mapFlagToDTO(flag)));
                        return dto;
                })
                .orElse(null);
    }

    private FeedBackDTO mapFlagToFeedbackDTO(Flag flag) {
        return feedBackRepo.findById(UUID.fromString(flag.getFlagedOnFeedBackId()))
                .map(fb ->{
                FeedBackDTO dto = new FeedBackDTO();
                        dto.setId(fb.getId().toString());
                        dto.setQuestionId(fb.getQuestionId());
                        dto.setAnswerId(fb.getAnswerId());
                        dto.setGivenBy(fb.getGivenBy().getId().toString());
                        dto.setFeedback(fb.getFeedback());
                        dto.setCreatedAt(fb.getCreatedAt());
                        dto.setFlagDTOs(List.of(mapFlagToDTO(flag)));
                        return dto;
                })
                .orElse(null);
    }

    private FlagDTO mapFlagToDTO(Flag flag) {
        FlagDTO dto = new FlagDTO();
                dto.setId(flag.getId().toString());
                dto.setFlagedById(flag.getFlagedById());
                dto.setFlagedRession(flag.getFlagedRession());
                dto.setFlagedOnQuestionId(flag.getFlagedOnQuestionId());
                dto.setFlagedOnAnswerId(flag.getFlagedOnAnswerId());
                dto.setFlagedOnFeedBackId(flag.getFlagedOnFeedBackId());
              
        return dto;
    }
}
