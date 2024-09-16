package hoversprite.notification.internal.dto;

import hoversprite.notification.external.dto.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNotificationsResponseDto {

    private String message;

    private List<NotificationDto> notifications;

}
