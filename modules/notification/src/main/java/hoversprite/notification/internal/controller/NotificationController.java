package hoversprite.notification.internal.controller;

import hoversprite.notification.internal.dto.GetNotificationsResponseDto;
import hoversprite.notification.internal.service.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {
    @Autowired
    private NotificationServiceImpl notificationService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-notifications")
    ResponseEntity<GetNotificationsResponseDto> getMyNotifications() {
        return new ResponseEntity<>(new GetNotificationsResponseDto("Notifications retrieved successfully", notificationService.getMyNotifications()), HttpStatus.OK);
    }
}
