package enterprise.hoversprite.modules.order;

import enterprise.hoversprite.modules.order.dtos.request.CreateOrderRequestDTO;
import enterprise.hoversprite.modules.order.dtos.response.CreateOrderResponseDTO;
import enterprise.hoversprite.modules.order.dtos.response.GetOrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

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
