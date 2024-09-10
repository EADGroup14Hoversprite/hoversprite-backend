package shared.dtos;

import shared.enums.CropType;
import shared.enums.OrderSlot;
import shared.enums.OrderStatus;
import shared.types.Location;

import java.time.LocalDate;
import java.util.List;

public interface OrderDto {
    Long getId();

    Long getFarmerId();

    CropType getCropType();

    Float getFarmlandArea();

    String getAddress();

    Location getLocation();

    LocalDate getDesiredDate();

    Float getTotalCost();

    OrderSlot getTimeSlot();

    OrderStatus getStatus();

    List<Long> getAssignedSprayerIds();

    Integer getSession();

    LocalDate getCreatedAt();

    LocalDate getUpdatedAt();
}
