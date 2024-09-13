package hoversprite.feedback.internal.service;

import hoversprite.order.external.dto.OrderDto;
import hoversprite.order.external.service.OrderService;
import hoversprite.feedback.internal.model.Feedback;
import hoversprite.feedback.internal.repository.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import hoversprite.feedback.external.dto.FeedbackDto;
import hoversprite.feedback.internal.enums.FeedbackSatisfactionRating;
import hoversprite.feedback.external.service.FeedbackService;
import hoversprite.common.external.util.UtilFunctions;

import java.util.List;
import java.util.Objects;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private OrderService orderService;

    public FeedbackDto createFeedback(Long orderId, String content, FeedbackSatisfactionRating satisfactionRating, Integer attentive, Integer friendly, Integer professional, List<String> imageUrls) throws Exception {
        OrderDto orderDto = orderService.getOrderById(orderId);
        orderService.updateOrderFeedback(orderDto.getId());
        UserDetails userDetails = UtilFunctions.getUserDetails();
        if (!Objects.equals(orderDto.getBookerId(), Long.valueOf(userDetails.getUsername()))) {
            throw new BadRequestException("You are not the booker associated with this order.");
        }
        Feedback feedback = new Feedback(null, orderId, content, satisfactionRating, attentive, friendly, professional, imageUrls);
        return feedbackRepository.save(feedback);
    }


    @Override
    public FeedbackDto getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback with this id not found"));
    }

    public List<FeedbackDto> getFeedbacksByOrderId(Long orderId) {
        return feedbackRepository.findAllByOrderId(orderId).stream().map(entity -> (FeedbackDto) entity).toList();
    }
}
