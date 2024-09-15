package hoversprite.notification.external.dto;

import java.time.LocalDate;

public interface NotificationDto {
    Long getId();

    Long getUserId();

    String getContent();

    LocalDate getSentAt();
}
