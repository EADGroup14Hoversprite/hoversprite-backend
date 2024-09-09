package shared.services;

import shared.dtos.FeedbackDto;

public interface FeedbackService {

    FeedbackDto getFeedbackById(Long id);
}
