package internal.model;

import api.order.dtos.OrderInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shared.enums.CropType;
import shared.enums.OrderSlot;
import shared.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
public class Order implements OrderInfoDTO {
    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long farmerId;

    @Enumerated(EnumType.STRING)
    private CropType cropType;

    private Float farmlandArea;

    private LocalDate desiredDate;

    private Float totalCost;

    @Enumerated(EnumType.STRING)
    private OrderSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private List<Long> assignedSprayerIds;

    private Integer session;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

}
