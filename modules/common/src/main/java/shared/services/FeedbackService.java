package shared.services;

import shared.dtos.feedback.FeedbackDTO;

public interface FeedbackService {
    FeedbackDTO createFeedbackByOrderId();

}
