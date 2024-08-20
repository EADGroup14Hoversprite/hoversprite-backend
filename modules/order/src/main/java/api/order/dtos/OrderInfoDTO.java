package api.order.dtos;

import lombok.AllArgsConstructor;
import shared.enums.CropType;
import shared.enums.OrderSlot;
import shared.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderInfoDTO {
    Long getId();

    Long getFarmerId();

    CropType getCropType();

    Float getFarmlandArea();

    LocalDate getDesiredDate();

    Float getTotalCost();

    OrderSlot getTimeSlot();

    OrderStatus getStatus();

    List<Long> getAssignedSprayerIds();

    Integer getSession();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();
}
