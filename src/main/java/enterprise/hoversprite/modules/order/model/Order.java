package enterprise.hoversprite.modules.order.model;

import enterprise.hoversprite.modules.order.enums.OrderSlot;
import enterprise.hoversprite.modules.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long farmerId;
    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long assignedSprayerId;

}
