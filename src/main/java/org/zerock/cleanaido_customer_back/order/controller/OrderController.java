package org.zerock.cleanaido_customer_back.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.common.dto.ApproveResponse;
import org.zerock.cleanaido_customer_back.common.dto.ReadyResponse;
import org.zerock.cleanaido_customer_back.order.dto.OrderDTO;
import org.zerock.cleanaido_customer_back.order.dto.PurchaseDTO;
import org.zerock.cleanaido_customer_back.order.service.KakaoPayService;
import org.zerock.cleanaido_customer_back.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mypage/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Log4j2
public class OrderController {

    private final OrderService orderService;
    private final KakaoPayService kakaoPayService;
    private String tid;
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

    @PostMapping("/pay/ready")
    public @ResponseBody ReadyResponse payReady(
            @RequestParam int totalPrice
    ) {

        log.info("주문 금액: " + totalPrice);

        // 카카오 결제 준비하기
        ReadyResponse readyResponse = kakaoPayService.payReady(totalPrice);
        log.info(readyResponse);
        // 세션에 결제 고유번호(tid) 저장
        tid = readyResponse.getTid();
        log.info("결제 고유번호: " + readyResponse.getTid());


        return readyResponse;
    }


    @GetMapping(value ="/pay/completed", produces = MediaType.TEXT_HTML_VALUE)
    public String payCompleted(@RequestParam("pg_token") String pgToken) {

        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        log.info("결제 고유번호: " + tid);

        // 카카오 결제 요청하기
        ApproveResponse approveResponse = kakaoPayService.payApprove(tid, pgToken);
//
        log.info("승인 완료 정보"+approveResponse);
//        Map<String, String> result = Map.of("result", approveResponse.getAid());

        return """
                <script> 
                    alert('주문이 완료되었습니다')
                    window.location = "http://10.10.10.148:5173/mypage/order/complete";
                </script>
                """;
    }
}
