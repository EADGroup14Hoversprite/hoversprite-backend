package hoversprite.order.external.dto;

import hoversprite.common.external.enums.CropType;
import hoversprite.common.external.enums.OrderSlot;
import hoversprite.common.external.enums.OrderStatus;
import hoversprite.common.external.enums.PaymentMethod;
import hoversprite.common.external.type.Location;

import java.time.LocalDate;
import java.util.List;

public interface OrderDto {
    Long getId();

    Long getBookerId();

    CropType getCropType();

    Double getFarmlandArea();

    String getAddress();

    Location getLocation();

    LocalDate getDesiredDate();

    Double getTotalCost();

    OrderSlot getTimeSlot();

    OrderStatus getStatus();

    List<Long> getAssignedSprayerIds();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();

    Boolean getPaymentStatus();

    PaymentMethod getPaymentMethod();

    String getFarmerName();

    String getFarmerPhoneNumber();

    Boolean getHasFeedback();
}
