package hoversprite.notification.internal.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hoversprite.common.external.serializer.LocalDateToEpochSerializer;
import hoversprite.notification.external.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements NotificationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @JsonSerialize(using = LocalDateToEpochSerializer.class)
    private LocalDate sentAt;

}
