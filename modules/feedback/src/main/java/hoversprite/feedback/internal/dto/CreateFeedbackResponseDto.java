package hoversprite.feedback.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.feedback.external.dto.FeedbackDto;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeedbackResponseDto {
    private String message;

    private FeedbackDto dto;
}
