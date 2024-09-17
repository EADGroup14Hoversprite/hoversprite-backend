package hoversprite.notification.internal.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import hoversprite.common.external.util.UtilFunctions;
import hoversprite.notification.external.dto.NotificationDto;
import hoversprite.notification.external.service.NotificationService;
import hoversprite.notification.internal.config.SocketIOHandler;
import hoversprite.notification.internal.model.Notification;
import hoversprite.notification.internal.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SocketIOHandler socketIOHandler;

    @Override
    public void sendNotificationToUser(String userId, String content) throws Exception {
        Notification notification = new Notification(null, Long.valueOf(userId), content, LocalDate.now());
        notificationRepository.save(notification);
        SocketIOServer socketIOServer = socketIOHandler.getSocketIOServer();
        for (SocketIOClient client : socketIOServer.getAllClients()) {
            String clientId = client.get("userId");
            if (userId.equals(clientId)) {
                client.sendEvent("notification", content);
                break;
            }
        }
    }

    @Override
    public void broadcastNotificationToAllUsersExceptTrigger(String excludedUserId, String content) throws Exception {
        SocketIOServer socketIOServer = socketIOHandler.getSocketIOServer();
        for (SocketIOClient client : socketIOServer.getAllClients()) {
            String clientUserId = client.get("userId");
            if (!clientUserId.equals(excludedUserId)) {
                client.sendEvent("notification", content);
            }
        }
    }

    public List<NotificationDto> getMyNotifications() {
        UserDetails userDetails = UtilFunctions.getUserDetails();
        Long userId = Long.valueOf(userDetails.getUsername());
        return notificationRepository.findAllByUserId(userId).stream().map(entity -> (NotificationDto) entity).toList();
    }
}
