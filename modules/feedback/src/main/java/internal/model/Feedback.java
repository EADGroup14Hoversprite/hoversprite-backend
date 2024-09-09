package internal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.enums.FeedbackSatisfactionRating;

@Entity
@Table(name = "feedbacks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private FeedbackSatisfactionRating satisfactionRating;

}
