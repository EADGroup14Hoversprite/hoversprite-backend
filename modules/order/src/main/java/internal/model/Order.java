package internal.model;

import shared.dtos.order.OrderDTO;
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
@NoArgsConstructor
public class Order implements OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long farmerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CropType cropType;

    @Column(nullable = false)
    private Float farmlandArea;

    @Column(nullable = false)
    private LocalDate desiredDate;

    @Column(nullable = false)
    private Float totalCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
