package internal.controller;

import api.order.dtos.OrderInfoDTO;
import internal.dtos.response.GetOrdersByFarmerIdResponseDTO;
import internal.dtos.request.CreateOrderRequestDTOImpl;
import internal.dtos.response.CreateOrderResponseDTO;
import internal.dtos.response.GetOrderResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import api.order.OrderService;


@Tag(name = "Order API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTOImpl dto) {
        OrderInfoDTO infoDto = orderService.createOrder(dto);
        return new ResponseEntity<>(new CreateOrderResponseDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<GetOrderResponseDTO> getOrderById(@PathVariable Long id) {
        OrderInfoDTO infoDto = orderService.getOrderById(id);
        return new ResponseEntity<>(new GetOrderResponseDTO(), HttpStatus.OK);
    }

    @GetMapping("/my-orders")
    ResponseEntity<GetOrdersByFarmerIdResponseDTO> getOrdersByFarmerId() {
        return new ResponseEntity<>(new GetOrdersByFarmerIdResponseDTO(), HttpStatus.OK);
    }
}