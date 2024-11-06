package org.zerock.cleanaido_customer_back.customer.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.customer.dto.CustomerListDTO;

@RestController
@RequestMapping("/api/v1/customer")
@Log4j2
@RequiredArgsConstructor
public class CustomerController {

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<CustomerListDTO>> list()
    {
        log.info("list");
        return null;
    }

}
