package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.enums.FeedbackSatisfactionRating;

@Data
@AllArgsConstructor
public class CreateFeedbackRequestDto {
    String content;

    FeedbackSatisfactionRating satisfactionRating;
}
