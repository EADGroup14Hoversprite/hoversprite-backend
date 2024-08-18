package enterprise.hoversprite.modules.order;

import enterprise.hoversprite.modules.order.enums.OrderSlot;
import enterprise.hoversprite.modules.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
class Order {
    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long farmerId;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private OrderSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long assignedSprayerId;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDate createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
