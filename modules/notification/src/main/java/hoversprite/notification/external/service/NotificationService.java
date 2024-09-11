package hoversprite.notification.external.service;

public interface NotificationService {
    void sendNotification(String userId, String content) throws Exception;
}
