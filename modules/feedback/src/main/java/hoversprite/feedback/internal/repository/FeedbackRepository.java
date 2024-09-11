package hoversprite.feedback.internal.repository;

import hoversprite.feedback.internal.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
