package internal.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import shared.dtos.OrderDto;
import lombok.NoArgsConstructor;
import shared.enums.CropType;
import shared.enums.OrderSlot;
import shared.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import shared.serializer.LocalDateToEpochSerializer;
import shared.types.Location;

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
    private Long farmerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CropType cropType;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Location location;

    @Column(nullable = false)
    private Float farmlandArea;

    @Column(nullable = false)
    @JsonSerialize(using = LocalDateToEpochSerializer.class)
    private LocalDate desiredDate;

    @Column(nullable = false)
    private Float totalCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ElementCollection
    private List<Long> assignedSprayerIds;

    private Integer session;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    @JsonSerialize(using = LocalDateToEpochSerializer.class)
    private LocalDate createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonSerialize(using = LocalDateToEpochSerializer.class)
    private LocalDate updatedAt;

}
