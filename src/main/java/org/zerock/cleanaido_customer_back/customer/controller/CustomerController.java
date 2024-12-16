package org.zerock.cleanaido_customer_back.customer.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.customer.dto.CustomerListDTO;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.service.CustomerService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/info")
    public ResponseEntity<?> getCustomerInfo() {
        Customer customer = customerService.getCustomerInfo();

        return ResponseEntity.ok(Map.of(
                "customerId", customer.getCustomerId(),
                "customerName", customer.getCustomerName(),
                "phoneNumber", customer.getPhoneNumber(),
                "address", customer.getAddress()
        ));
    }

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<CustomerListDTO>> list()
    {
        log.info("list");
        return null;
    }

    // fcm 토큰 저장
    @PutMapping("fcm")
    public ResponseEntity<String> update(
            @RequestHeader("customer-id") String customerId,
            @RequestHeader("fcm-token") String fcmToken
    ) {
        log.info(customerId);
        log.info(fcmToken);

        customerService.updateFcmToken(customerId, fcmToken);

        return ResponseEntity.ok(customerId + "님의 fcm_token이 저장되었습니다.");
    }

}
