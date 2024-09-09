package shared.dtos;

import shared.enums.FeedbackSatisfactionRating;

public interface FeedbackDto {
    Long getId();

    Long getOrderId();

    String getContent();

    FeedbackSatisfactionRating getSatisfactionRating();
}
