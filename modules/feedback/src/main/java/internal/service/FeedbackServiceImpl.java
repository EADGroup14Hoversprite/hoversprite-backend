package internal.service;

import internal.model.Feedback;
import internal.repository.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import shared.dtos.FeedbackDto;
import shared.dtos.OrderDto;
import shared.enums.FeedbackSatisfactionRating;
import shared.services.FeedbackService;
import shared.services.OrderService;
import shared.utils.UtilFunctions;

import java.util.Objects;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private OrderService orderService;

    public FeedbackDto createFeedback(Long orderId, String content, FeedbackSatisfactionRating satisfactionRating) throws Exception {
        OrderDto orderDto = orderService.getOrderById(orderId);
        UserDetails userDetails = UtilFunctions.getUserDetails();
        if (!Objects.equals(orderDto.getFarmerId(), Long.valueOf(userDetails.getUsername()))) {
            throw new BadRequestException("You are not the farmer associated with this order.");
        }
        Feedback feedback = new Feedback(null, orderId, content, satisfactionRating);
        return feedbackRepository.save(feedback);
    }


    @Override
    public FeedbackDto getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback with this id not found"));
    }
}
