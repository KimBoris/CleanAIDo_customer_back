
package org.zerock.cleanaido_customer_back.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.order.dto.OrderDTO;
import org.zerock.cleanaido_customer_back.order.dto.PurchaseDTO;
import org.zerock.cleanaido_customer_back.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody PurchaseDTO purchaseDTO) {
        OrderDTO orderDTO = orderService.placeOrder(purchaseDTO);
        return ResponseEntity.ok(orderDTO);
    }

    // 고객 주문 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@RequestParam Long customerId) {
        List<OrderDTO> orderList = orderService.getCustomerOrders(customerId);
        return ResponseEntity.ok(orderList);
    }
}
