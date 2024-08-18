package controller;

import dtos.request.CreateOrderRequestDTO;
import dtos.response.CreateOrderResponseDTO;
import dtos.response.GetOrderResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Order API")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/order")
class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO dto) {
        return new ResponseEntity<>(new CreateOrderResponseDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderResponseDTO> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(new GetOrderResponseDTO(), HttpStatus.OK);
    }
}
