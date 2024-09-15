package hoversprite.notification.external.service;

public interface NotificationService {
    void sendNotificationToUser(String userId, String content) throws Exception;

    void broadcastNotificationToAllUsersExceptTrigger(String userId, String content) throws Exception;
}
