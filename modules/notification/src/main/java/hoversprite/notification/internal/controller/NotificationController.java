package hoversprite.notification.internal.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {
}
