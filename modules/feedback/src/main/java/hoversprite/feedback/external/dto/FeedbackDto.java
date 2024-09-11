package hoversprite.feedback.external.dto;

import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;

public interface FeedbackDto {
    Long getId();

    Long getOrderId();

    String getContent();

    FeedbackSatisfactionRating getSatisfactionRating();
}
