package hoversprite.feedback.internal.controller;

import hoversprite.feedback.internal.dto.CreateFeedbackRequestDto;
import hoversprite.feedback.internal.dto.CreateFeedbackResponseDto;
import hoversprite.feedback.internal.dto.GetFeedbacksByOrderIdResponseDto;
import hoversprite.feedback.internal.service.FeedbackServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import hoversprite.feedback.external.dto.FeedbackDto;

@Tag(name = "Feedback API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/feedback")
@CrossOrigin("*")
class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @PreAuthorize("hasRole('USER') and hasRole('FARMER')")
    @PostMapping()
    ResponseEntity<CreateFeedbackResponseDto> createFeedback(@Valid @ModelAttribute CreateFeedbackRequestDto dto) throws Exception {
        FeedbackDto feedbackDto = feedbackService.createFeedback(dto.getOrderId(), dto.getContent(), dto.getSatisfactionRating(), dto.getAttentive(), dto.getFriendly(), dto.getProfessional(), dto.getImages());
        return new ResponseEntity<>(new CreateFeedbackResponseDto("Feedback successfully created", feedbackDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    ResponseEntity<GetFeedbacksByOrderIdResponseDto> getFeedbacksByOrderId(@RequestParam Long orderId) {
        return new ResponseEntity<>(new GetFeedbacksByOrderIdResponseDto("Feedback retrieved successfully", feedbackService.getFeedbacksByOrderId(orderId)), HttpStatus.OK);
    }

}
