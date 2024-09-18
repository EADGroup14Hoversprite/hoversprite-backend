package hoversprite.order.internal.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hoversprite.common.external.enums.CropType;
import hoversprite.common.external.enums.OrderSlot;
import hoversprite.common.external.enums.OrderStatus;
import hoversprite.common.external.enums.PaymentMethod;
import hoversprite.order.external.dto.OrderDto;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import hoversprite.common.external.serializer.LocalDateToEpochSerializer;
import hoversprite.common.external.type.Location;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements OrderDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CropType cropType;

    @Column(nullable = false)
    private String farmerName;

    @Column(nullable = false)
    private String farmerPhoneNumber;

    @Column(nullable = false)
    private String farmerEmailAddress;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Location location;

    @Column(nullable = false)
    private Double farmlandArea;

    @Column(nullable = false)
    @JsonSerialize(using = LocalDateToEpochSerializer.class)
    private LocalDate desiredDate;

    @Column(nullable = false)
    private Double totalCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderSlot timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private Boolean paymentStatus;

    @ElementCollection
    private List<Long> assignedSprayerIds;

    private Boolean hasFeedback;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    @JsonSerialize(using = LocalDateToEpochSerializer.class)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonSerialize(using = LocalDateToEpochSerializer.class)
    private LocalDate updatedAt;

}
