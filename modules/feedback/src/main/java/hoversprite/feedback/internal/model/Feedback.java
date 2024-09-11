package hoversprite.feedback.internal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import hoversprite.feedback.external.dto.FeedbackDto;
import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;

@Entity
@Table(name = "feedbacks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements FeedbackDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private FeedbackSatisfactionRating satisfactionRating;

    @Column(nullable = false)
    private Integer attentive;

    @Column(nullable = false)
    private Integer friendly;

    @Column(nullable = false)
    private Integer professional;
}
