package org.zerock.cleanaido_customer_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "org.zerock.cleanaido_customer_back.order.entity",
        "org.zerock.cleanaido_customer_back.product.entity",
        "org.zerock.cleanaido_customer_back.customer.entity",
        "org.zerock.cleanaido_customer_back.cart.entity",
        "org.zerock.cleanaido_customer_back.product.entity",
        "org.zerock.cleanaido_customer_back.category.entity"
})


public class CleanAiDoCustomerBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CleanAiDoCustomerBackApplication.class, args);
    }
}

