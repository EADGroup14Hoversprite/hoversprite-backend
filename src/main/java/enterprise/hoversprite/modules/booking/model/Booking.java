package enterprise.hoversprite.modules.booking.model;

import java.util.Date;

import enterprise.hoversprite.modules.booking.enums.BookingSlot;
import enterprise.hoversprite.modules.booking.enums.BookingStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long farmerId;
    private Date date;

    @Enumerated(EnumType.STRING)
    private BookingSlot timeSlot;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking() {
    }

    public Booking(Long id, Long farmerId, Date date, BookingSlot timeSlot, BookingStatus status) {
        this.id = id;
        this.farmerId = farmerId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BookingSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(BookingSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

}
