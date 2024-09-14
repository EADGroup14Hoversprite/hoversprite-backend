package hoversprite.feedback.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;
import org.springframework.web.multipart.MultipartFile;

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

    private List<MultipartFile> imageUrls;

}
