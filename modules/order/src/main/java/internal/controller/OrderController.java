package internal.controller;

import internal.dtos.*;
import org.springframework.security.access.prepost.PreAuthorize;
import shared.dtos.order.OrderDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shared.services.OrderService;

import java.util.List;


@Tag(name = "Order API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/order")
class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('USER') and hasAnyRole('FARMER', 'RECEPTIONIST')")
    @PostMapping("/create")
    ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTOImpl dto) throws Exception {
        OrderDTO orderDto = orderService.createOrder(dto);
        return new ResponseEntity<>(new CreateOrderResponseDTO(orderDto == null ? "This time slot is full": "Order created successfully", orderDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') and hasRole('RECEPTIONIST')")
    @PostMapping("/{id}/confirm")
    ResponseEntity<ConfirmOrderResponseDTO> confirmOrder(@PathVariable Long id) throws Exception {
        OrderDTO orderDto = orderService.confirmOrder(id);
        return new ResponseEntity<>(new ConfirmOrderResponseDTO("Order confirmed successfully", orderDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER', 'ADMIN)")
    @GetMapping("/{id}")
    ResponseEntity<GetOrderResponseDTO> getOrderById(@PathVariable Long id) throws Exception {
        OrderDTO orderDto = orderService.getOrderById(id);
        return new ResponseEntity<>(new GetOrderResponseDTO("Order retrieved successfully", orderDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('FARMER')")
    @GetMapping("/my-orders")
    ResponseEntity<GetOrdersByFarmerIdResponseDTO> getOrdersByFarmerId() throws Exception {
        List<OrderDTO> orderDtos = orderService.getOrdersByFarmerId();
        return new ResponseEntity<>(new GetOrdersByFarmerIdResponseDTO("Orders retrieved successfully", orderDtos), HttpStatus.OK);
    }

}
