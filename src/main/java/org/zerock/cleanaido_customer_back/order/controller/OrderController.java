package org.zerock.cleanaido_customer_back.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.order.dto.OrderDTO;
import org.zerock.cleanaido_customer_back.order.dto.PurchaseDTO;
import org.zerock.cleanaido_customer_back.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage/order")
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
    public ResponseEntity<List<OrderDTO>> getCustomerOrders(@RequestParam String customerId) {
        List<OrderDTO> orderList = orderService.getCustomerOrders(customerId);
        return ResponseEntity.ok(orderList);
    }

    @PatchMapping("/{orderNumber}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderNumber,
            @RequestParam String status) {
        orderService.updateOrderStatus(orderNumber, status);
        return ResponseEntity.ok("Order status updated to " + status);
    }
}
