package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.dtos.FeedbackDto;

@Data
@AllArgsConstructor
public class CreateFeedbackResponseDto {
    private String message;

    private FeedbackDto dto;
}
