package org.zerock.cleanaido_customer_back;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.customer.dto.CustomerListDTO;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static java.time.LocalDateTime.*;

@SpringBootTest
class CleanAiDoCustomerBackApplicationTests {

    @Autowired
    CustomerRepository repository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Commit
    @Transactional
    public void addProduct()
    {
        ProductListDTO dto = new ProductListDTO();
        IntStream.rangeClosed(0,20).forEach(i->{
            Product product = Product.builder()
                    .pcode("PCOde"+i)
                    .pname("Product" + i)
                    .price(2000)
                    .quantity(i)
                    .createdAt(LocalDateTime.of(2024,11,6,12, 59))
                    .updatedAt(LocalDateTime.of(2024,11,6,12, 59))
                    .releasedAt(LocalDateTime.of(2024,11,6,12, 59))
                    .pstatus("판매중")
                    .ptags("테스트")
                    .sellerId("판매자"+i)
                    .build();


            productRepository.save(product);
        });
    }

//    @Test
//    @Commit
//    @Transactional
//    public void addFile() {
//
//
//        CustomerListDTO dto = new CustomerListDTO();
//        IntStream.rangeClosed(0, 20).forEach(i -> {
//            Customer customer = Customer.builder()
//                    .customerId("Customer1")
//                    .customerName("Customer")
//                    .customerPw("1111")
//                    .birthDate(LocalDate.now())
//                    .createDate(Timestamp.valueOf(now()))
//                    .updatedDate(Timestamp.valueOf(now()))
//                    .phoneNumber("010-3267-2442")
//                    .delFlag(false)
//                    .address("부산광역시 부산진구 양정동")
//                    .profileImageUrl("Profile1")
//                    .build();
//
//            repository.save(customer);
//        });
//
//
//    }

}
