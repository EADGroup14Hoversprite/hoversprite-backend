package internal.controller;

import internal.dtos.*;
import internal.service.OrderServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import shared.dtos.OrderDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shared.dtos.UserDto;

import java.util.List;


@Tag(name = "Order API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/order")
class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PreAuthorize("hasRole('USER') and hasAnyRole('FARMER', 'RECEPTIONIST')")
    @PostMapping("/create")
    ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto dto) throws Exception {
        OrderDto orderDto = orderService.createOrder(dto.getFarmerId(), dto.getCropType(), dto.getAddress(), dto.getLocation(), dto.getFarmlandArea(), dto.getDesiredDate(), dto.getTimeSlot());
        return new ResponseEntity<>(new CreateOrderResponseDto(orderDto == null ? "This time slot is full": "Order created successfully", orderDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/update-status")
    ResponseEntity<UpdateOrderStatusResponseDto> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusRequestDto dto) throws Exception {
        OrderDto orderDto = orderService.updateOrderStatus(id, dto.getStatus());
        return new ResponseEntity<>(new UpdateOrderStatusResponseDto("Order status successfully set to " + orderDto.getStatus(), orderDto), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER') and hasRole('RECEPTIONIST')")
    @PostMapping("/{id}/assign-sprayer")
    ResponseEntity<AssignSprayerResponseDto> assignSprayer(@PathVariable Long id, @RequestBody AssignSprayerRequestDto dto) throws Exception {
        OrderDto orderDto = orderService.assignSprayer(id, dto.getSprayerIds());
        return new ResponseEntity<>(new AssignSprayerResponseDto("Assigned sprayers to order successfully", orderDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<GetOrderResponseDto> getOrderById(@PathVariable Long id) throws Exception {
        OrderDto orderDto = orderService.getOrderById(id);
        return new ResponseEntity<>(new GetOrderResponseDto("Order retrieved successfully", orderDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('FARMER')")
    @GetMapping("/my-orders")
    ResponseEntity<GetOrdersByFarmerIdResponseDto> getOrdersByFarmerId() throws Exception {
        List<OrderDto> orderDtos = orderService.getOrdersByFarmerId();
        return new ResponseEntity<>(new GetOrdersByFarmerIdResponseDto("Orders retrieved successfully", orderDtos), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('RECEPTIONIST')")
    @PostMapping("/{id}/suggested-sprayers")
    ResponseEntity<GetSuggestedSprayersResponseDto> getSuggestedSprayers(@PathVariable Long id, @RequestBody GetSuggestedSprayersRequestDto dto) throws Exception {
        List<UserDto> sprayerDtos = orderService.getSuggestedSprayers(id, dto.getStartDate(), dto.getEndDate());
        if (sprayerDtos.isEmpty()) {
            return new ResponseEntity<>(new GetSuggestedSprayersResponseDto("No suitable sprayers suggested for this order", null), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new GetSuggestedSprayersResponseDto("Successfully found suitable sprayers for this order", sprayerDtos), HttpStatus.OK);
    }

}
