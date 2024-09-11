package hoversprite.feedback.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;

@Data
@AllArgsConstructor
public class CreateFeedbackRequestDto {

    private Long orderId;

    private String content;

    private FeedbackSatisfactionRating satisfactionRating;

    private Integer attentive;

    private Integer friendly;

    private Integer professional;
}
