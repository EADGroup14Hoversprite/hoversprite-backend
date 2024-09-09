package internal.service;

import internal.model.Feedback;
import internal.repository.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shared.dtos.FeedbackDto;
import shared.enums.FeedbackSatisfactionRating;
import shared.services.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public FeedbackDto createFeedback(Long orderId, String content, FeedbackSatisfactionRating satisfactionRating) {
        Feedback feedback = new Feedback(null, orderId, content, satisfactionRating);
        return feedbackRepository.save(feedback);
    }


    @Override
    public FeedbackDto getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback with this id not found"));
    }
}
