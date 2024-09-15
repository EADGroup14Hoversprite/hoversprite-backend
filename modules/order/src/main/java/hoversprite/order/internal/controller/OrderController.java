package hoversprite.order.internal.controller;

import hoversprite.order.internal.dto.*;
import hoversprite.order.internal.model.Order;
import hoversprite.order.internal.service.OrderServiceImpl;
import hoversprite.user.external.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import hoversprite.order.external.dto.OrderDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


@Tag(name = "Order API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/order")
@CrossOrigin("*")
class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PreAuthorize("hasRole('USER') and hasAnyRole('FARMER', 'RECEPTIONIST')")
    @PostMapping
    ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto dto) throws Exception {
        OrderDto orderDto = orderService.createOrder(dto.getCropType(), dto.getFarmerName(), dto.getFarmerPhoneNumber(), dto.getAddress(), dto.getLocation(), dto.getFarmlandArea(), dto.getDesiredDate(), dto.getTimeSlot());
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    ResponseEntity<GetOrderResponseDto> getOrderById(@PathVariable Long id) throws Exception {
        OrderDto orderDto = orderService.getOrderById(id);
        return new ResponseEntity<>(new GetOrderResponseDto("Order retrieved successfully", orderDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasAnyRole('FARMER', 'RECEPTIONIST')")
    @GetMapping("/my-orders")
    ResponseEntity<GetOrdersResponseDto> getOrdersByBookerId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "status") String sortBy, @RequestParam(defaultValue = "ASC") String sortDirection) throws Exception {
        Page<Order> orderPage = orderService.getOrdersByBookerId(page, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(new GetOrdersResponseDto("Orders retrieved successfully", orderPage.getContent().stream().map(entity -> (OrderDto) entity).toList(), orderPage.getTotalPages()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/by-date-range")
    ResponseEntity<GetOrdersResponseDto> getOrdersByDateRange(@RequestParam long startDate, @RequestParam long endDate) {
        LocalDate startDateConverted = Instant.ofEpochSecond(startDate).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateConverted = Instant.ofEpochSecond(endDate).atZone(ZoneId.systemDefault()).toLocalDate();
        List<OrderDto> orderDtos = orderService.getOrdersWithinDateRange(startDateConverted, endDateConverted);
        if (orderDtos.isEmpty()) {
            return new ResponseEntity<>(new GetOrdersResponseDto("No orders within time range found", null, null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new GetOrdersResponseDto("Orders retrieved successfully", orderDtos, null), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('RECEPTIONIST')")
    @GetMapping("/{id}/suggested-sprayers")
    ResponseEntity<GetSuggestedSprayersResponseDto> getSuggestedSprayers(@PathVariable Long id, @RequestParam long startDate, @RequestParam long endDate) throws Exception {
        LocalDate startDateConverted = Instant.ofEpochSecond(startDate).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateConverted = Instant.ofEpochSecond(endDate).atZone(ZoneId.systemDefault()).toLocalDate();
        List<UserDto> sprayerDtos = orderService.getSuggestedSprayers(id, startDateConverted, endDateConverted);
        if (sprayerDtos.isEmpty()) {
            return new ResponseEntity<>(new GetSuggestedSprayersResponseDto("No suitable sprayers suggested for this order", null), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new GetSuggestedSprayersResponseDto("Successfully found suitable sprayers for this order", sprayerDtos), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('FARMER')")
    @PostMapping("/{id}/create-payment")
    ResponseEntity<CreatePaymentResponseDto> createPayment(@PathVariable Long id, @RequestBody CreatePaymentRequestDto dto) throws Exception {
        return new ResponseEntity<>(new CreatePaymentResponseDto(orderService.createPayment(id, dto.getSuccessUrl(), dto.getCancelUrl())), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('FARMER')")
    @PostMapping("/{id}/execute-payment")
    ResponseEntity<ExecutePaymentResponseDto> executePayment(@PathVariable Long id, @RequestBody ExecutePaymentRequestDto dto) throws Exception {
        return new ResponseEntity<>(new ExecutePaymentResponseDto(orderService.executePayment(id, dto.getPaymentId(), dto.getPayerId())), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('SPRAYER')")
    @PostMapping("/{id}/confirm-cash-payment")
    ResponseEntity<ConfirmCashPaymentDto> confirmCashPayment(@PathVariable Long id) throws Exception{
        return new ResponseEntity<>(new ConfirmCashPaymentDto(orderService.confirmCashPayment(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('RECEPTIONIST')")
    @GetMapping()
    ResponseEntity<GetOrdersResponseDto> getAllOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "status") String sortBy, @RequestParam(defaultValue = "ASC") String sortDirection) {
        Page<Order> orderPage = orderService.getAllOrders(page, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(new GetOrdersResponseDto("Orders retrieved successfully", orderPage.getContent().stream().map(entity -> (OrderDto) entity).toList(), orderPage.getTotalPages()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('SPRAYER')")
    @GetMapping("/assigned")
    ResponseEntity<GetOrdersResponseDto> getOrdersBySprayerId() throws Exception {
        return new ResponseEntity<>(new GetOrdersResponseDto("Orders retrieved successfully", orderService.getOrdersBySprayerId(), null), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('SPRAYER')")
    @GetMapping("/{id}/send-otp")
    ResponseEntity<GetOtpConfirmationResponseDto> sendOtp(@PathVariable Long id) throws Exception {
        orderService.sendOtpToFarmer(id);
        return new ResponseEntity<>(new GetOtpConfirmationResponseDto("Otp sent to farmer. Please wait."), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') and hasRole('SPRAYER')")
    @GetMapping("/{id}/verify-otp")
    ResponseEntity<GetOtpConfirmationResponseDto> verifyOtp(@PathVariable Long id, @RequestBody VerifyOtpRequestDto dto) throws Exception {
        return new ResponseEntity<>(new GetOtpConfirmationResponseDto(orderService.verifyOtp(id, dto.getOtp()) ? "Successfully verified OTP, order is marked as complete.": "Failed to verify OTP, please try again."), HttpStatus.OK);
    }


//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/paging")
//    ResponseEntity<GetOrdersResponseDto> getAllOrdersPaginated(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10") String pageSize) {
//        return new ResponseEntity<>(new GetOrdersResponseDto("Orders retrieved successfully", ));
//    }

}

//farmer1
//eyJhbGciOiJIUzM4NCJ9.eyJhdXRoUm9sZSI6IlJPTEVfVVNFUiIsInVzZXJSb2xlIjoiUk9MRV9GQVJNRVIiLCJzdWIiOiI1IiwiaWF0IjoxNzI2MTI1ODMyLCJleHAiOjE3MjYxMjk0MzJ9.Jwh3dp2e3b9DWnNrfKfU08P3B2ZzUsh3rXq3lx4r1Pbsv-4FaVcsmI7CJaOSYZGO
//
//receptionist1
//eyJhbGciOiJIUzM4NCJ9.eyJhdXRoUm9sZSI6IlJPTEVfVVNFUiIsInVzZXJSb2xlIjoiUk9MRV9SRUNFUFRJT05JU1QiLCJzdWIiOiI2IiwiaWF0IjoxNzI2MTI1OTE5LCJleHAiOjE3MjYxMjk1MTl9.id6ldEhJVozACGcw1flJ3Tli-XMbuXBSrb4HdChEmWO4Z2IczQz-jY-E86NOONHK
//
//sprayer1 id 7
//eyJhbGciOiJIUzM4NCJ9.eyJhdXRoUm9sZSI6IlJPTEVfVVNFUiIsInVzZXJSb2xlIjoiUk9MRV9TUFJBWUVSIiwic3ViIjoiNyIsImlhdCI6MTcyNjEyNTk3OSwiZXhwIjoxNzI2MTI5NTc5fQ.Y8DZS13XyNL2ZvYco3ekBXtyCizQMlBtOAoz84C0scjNlt0cBCMuIk0z2lQG1BVd

