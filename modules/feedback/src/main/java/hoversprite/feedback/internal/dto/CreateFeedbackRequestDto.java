package hoversprite.feedback.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;

@Data
@AllArgsConstructor
public class CreateFeedbackRequestDto {
    String content;

    FeedbackSatisfactionRating satisfactionRating;
}
