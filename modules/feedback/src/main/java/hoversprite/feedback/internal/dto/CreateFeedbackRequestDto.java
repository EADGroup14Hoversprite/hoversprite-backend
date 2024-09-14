package hoversprite.feedback.internal.dto;

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

    private Integer attentive;

    private Integer friendly;

    private Integer professional;

    @Schema(type = "array", format = "binary")
    private List<MultipartFile> images;

}
