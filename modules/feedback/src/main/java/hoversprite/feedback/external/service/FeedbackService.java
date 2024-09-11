package hoversprite.feedback.external.service;


import hoversprite.feedback.external.dto.FeedbackDto;

public interface FeedbackService {

    FeedbackDto getFeedbackById(Long id);
}
