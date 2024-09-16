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
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SocketIOHandler socketIOHandler;

    @Override
    public void sendNotificationToUser(String userId, String content) throws Exception {
        SocketIOServer socketIOServer = socketIOHandler.getSocketIOServer();
        for (SocketIOClient client : socketIOServer.getAllClients()) {
            System.out.println(client);
            if (userId.equals(client.get("userId"))) {
                client.sendEvent("notification", content);
                Notification notification = new Notification(null, Long.valueOf(userId), content, LocalDate.now());
                notificationRepository.save(notification);
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
        Long userId = Long.valueOf(userDetails.getPassword());
        return notificationRepository.findAllByUserId(userId).stream().map(entity -> (NotificationDto) entity).toList();
    }
}
