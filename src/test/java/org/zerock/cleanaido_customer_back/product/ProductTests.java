package org.zerock.cleanaido_customer_back.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.cleanaido_customer_back.product.entity.Product;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Log4j2
@RequiredArgsConstructor
public class ProductTests {

    private Product product;

    @BeforeEach
    public void setUp()
    {
        product = Product.builder()
                .pno(1L)
                .pcode("CODE123")
                .pname("Test Product")
                .price(10000)
                .quantity(50)
                .releasedAt(LocalDateTime.now())
                .sellerId("seller123")
                .build();
    }



}
