package hoversprite.feedback.internal.controller;

import hoversprite.feedback.internal.dto.CreateFeedbackRequestDto;
import hoversprite.feedback.internal.dto.CreateFeedbackResponseDto;
import hoversprite.feedback.internal.dto.GetFeedbackByOrderIdResponseDto;
import hoversprite.feedback.internal.service.FeedbackServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import hoversprite.feedback.external.dto.FeedbackDto;

import java.util.List;

@Tag(name = "Feedback API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/feedback")
@CrossOrigin("*")
class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @PreAuthorize("hasRole('FARMER')")
    @PostMapping()
    ResponseEntity<CreateFeedbackResponseDto> createFeedback(@RequestBody CreateFeedbackRequestDto dto) throws Exception {
        FeedbackDto feedbackDto = feedbackService.createFeedback(dto.getOrderId(), dto.getContent(), dto.getSatisfactionRating(), dto.getAttentive(), dto.getFriendly(), dto.getProfessional(), dto.getImageUrls());
        return new ResponseEntity<>(new CreateFeedbackResponseDto("Feedback successfully created", feedbackDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('FARMER')")
    @GetMapping()
    ResponseEntity<GetFeedbackByOrderIdResponseDto> getFeedbacksByOrderId(@RequestParam Long orderId) {
        return new ResponseEntity<>(new GetFeedbackByOrderIdResponseDto("Feedback retrieved successfully", feedbackService.getFeedbacksByOrderId(orderId)), HttpStatus.OK);
    }

}
