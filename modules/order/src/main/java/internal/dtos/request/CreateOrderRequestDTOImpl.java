package internal.dtos.request;

import api.order.dtos.CreateOrderRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import shared.enums.CropType;
import shared.enums.OrderSlot;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateOrderRequestDTOImpl implements CreateOrderRequestDTO {

    //null if farmer is sending request
    private Long farmerId;

    private CropType cropType;

    private Float farmlandArea;

    private LocalDate desiredDate;

    private OrderSlot orderSlot;

    private Integer session;
}