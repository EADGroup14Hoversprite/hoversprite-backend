package hoversprite.notification.internal.service;

import hoversprite.notification.external.service.NotificationService;
import hoversprite.notification.internal.model.Notification;
import hoversprite.notification.internal.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendNotification(String userId, String content) throws Exception {
        System.out.print(userId);
        System.out.print(content);
        Notification notification = new Notification(null, Long.valueOf(userId), content, LocalDate.now());
        notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/test", "Test message to all clients");
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", content);
        System.out.println("Notification sent to user: " + userId);
    }
}
