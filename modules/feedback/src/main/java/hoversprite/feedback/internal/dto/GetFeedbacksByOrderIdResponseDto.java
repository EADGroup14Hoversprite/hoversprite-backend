package hoversprite.feedback.internal.dto;

import hoversprite.feedback.external.dto.FeedbackDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetFeedbacksByOrderIdResponseDto {
    private String message;

    private List<FeedbackDto> feedbacks;
}
