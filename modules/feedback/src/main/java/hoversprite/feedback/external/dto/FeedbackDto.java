package hoversprite.feedback.external.dto;

import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;

import java.util.List;

public interface FeedbackDto {
    Long getId();

    Long getOrderId();

    String getContent();

    FeedbackSatisfactionRating getSatisfactionRating();

    Integer getAttentive();

    Integer getFriendly();

    Integer getProfessional();

    List<String> getImageUrls();
}

