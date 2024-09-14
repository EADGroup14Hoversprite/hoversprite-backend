package hoversprite.notification.internal.service;

import hoversprite.common.external.util.UtilFunctions;
import hoversprite.notification.external.dto.NotificationDto;
import hoversprite.notification.external.service.NotificationService;
import hoversprite.notification.internal.config.WebSocketSessionManager;
import hoversprite.notification.internal.model.Notification;
import hoversprite.notification.internal.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private WebSocketSessionManager sessionManager;
    
    @Override
    public void sendNotificationToUser(String userId, String content) throws Exception {
        WebSocketSession session = sessionManager.getSession(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(content));
            Notification notification = new Notification(null, Long.valueOf(userId), content, LocalDate.now());
            notificationRepository.save(notification);
        }
    }

    @Override
    public void broadcastNotificationToAllUsersExceptTrigger(String userId, String content) throws Exception {
        for (WebSocketSession session: sessionManager.getAllSessions()) {
            String sessionUserId = (String) session.getAttributes().get("userId");
            if (sessionUserId.equals(userId)) {
                continue;
            }
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(content));
            }
        }
    }

    public List<NotificationDto> getMyNotifications() {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        return notificationRepository.findAllByUserId(Long.valueOf(userDetails.getPassword())).stream().map(entity -> (NotificationDto) entity).toList();
    }
}
