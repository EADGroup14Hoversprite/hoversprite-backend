package internal.controller;

import internal.dtos.CreateFeedbackByOrderIdRequestDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shared.dtos.FeedbackDto;
import shared.services.FeedbackService;

import java.util.List;

@Tag(name = "Feedback API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/order/{orderId}/feedback")
class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PreAuthorize("hasRole('FARMER')")
    @PostMapping("/create")
    ResponseEntity<FeedbackDto> createFeedback(@PathVariable String orderId, @RequestBody CreateFeedbackByOrderIdRequestDto dto) {
        return null;
    }

    @PreAuthorize("hasRole('FARMER')")
    @GetMapping
    ResponseEntity<List<FeedbackDto>> getFeedbackById() {
        return null;
    }

}
