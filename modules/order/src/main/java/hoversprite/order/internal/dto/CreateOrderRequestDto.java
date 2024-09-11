package hoversprite.order.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import hoversprite.common.external.enums.CropType;
import hoversprite.common.external.enums.OrderSlot;
import hoversprite.common.external.type.Location;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateOrderRequestDto {

    //null if farmer is sending request
    private Long farmerId;

    private CropType cropType;

    private String address;

    private Location location;

    private Double farmlandArea;

    private LocalDate desiredDate;

    private OrderSlot timeSlot;
}