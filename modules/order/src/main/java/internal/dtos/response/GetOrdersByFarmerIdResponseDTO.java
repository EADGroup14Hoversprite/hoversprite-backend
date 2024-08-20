package internal.dtos.response;

import api.order.dtos.OrderInfoDTO;

import java.util.List;

public class GetOrdersByFarmerIdResponseDTO {

    private String message;

    private List<OrderInfoDTO> orders;
}
