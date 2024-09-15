package hoversprite.feedback.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.feedback.external.dto.FeedbackDto;

@Data
@AllArgsConstructor
public class CreateFeedbackResponseDto {
    private String message;

    private FeedbackDto dto;
}
