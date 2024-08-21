package shared.dtos.order;

import shared.enums.CropType;
import shared.enums.OrderSlot;

import java.time.LocalDate;

public interface CreateOrderRequestDTO {
    Long getFarmerId();

    CropType getCropType();

    Float getFarmlandArea();

    LocalDate getDesiredDate();

    OrderSlot getTimeSlot();

}
