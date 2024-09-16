package hoversprite.feedback.internal.dto;

import hoversprite.feedback.external.dto.FeedbackDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFeedbacksByOrderIdResponseDto {
    private String message;

    private List<FeedbackDto> feedbacks;
}
