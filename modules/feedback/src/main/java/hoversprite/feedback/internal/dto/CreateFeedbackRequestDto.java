package hoversprite.feedback.internal.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateFeedbackRequestDto {

    private Long orderId;

    private String content;

    private FeedbackSatisfactionRating satisfactionRating;

    @Min(1)
    @Max(5)
    private Integer attentive;

    @Min(1)
    @Max(5)
    private Integer friendly;

    @Min(1)
    @Max(5)
    private Integer professional;

    @Schema(type = "array", format = "binary")
    private List<MultipartFile> images;

}
