package internal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import shared.enums.CropType;
import shared.enums.OrderSlot;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateOrderRequestDto {

    //null if farmer is sending request
    private Long farmerId;

    private CropType cropType;

    private Float farmlandArea;

    private LocalDate desiredDate;

    private OrderSlot timeSlot;
}
