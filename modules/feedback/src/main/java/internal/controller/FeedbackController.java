package internal.controller;

import internal.dtos.CreateFeedbackRequestDto;
import internal.dtos.CreateFeedbackResponseDto;
import internal.service.FeedbackServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shared.dtos.FeedbackDto;

import java.util.List;

@Tag(name = "Feedback API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/order/{orderId}/feedback")
@CrossOrigin("*")
class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @PreAuthorize("hasRole('FARMER')")
    @PostMapping()
    ResponseEntity<CreateFeedbackResponseDto> createFeedback(@PathVariable Long orderId, @RequestBody CreateFeedbackRequestDto dto) throws Exception {
        FeedbackDto feedbackDto = feedbackService.createFeedback(orderId, dto.getContent(), dto.getSatisfactionRating());
        return new ResponseEntity<>(new CreateFeedbackResponseDto("Feedback successfully created", feedbackDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('FARMER')")
    @GetMapping
    ResponseEntity<List<FeedbackDto>> getFeedbackByOrderId(@PathVariable String orderId) {
        return null;
    }

}
