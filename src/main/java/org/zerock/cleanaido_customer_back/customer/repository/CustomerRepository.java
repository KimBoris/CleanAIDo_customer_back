package org.zerock.cleanaido_customer_back.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByCustomerId(String customerId); // String 타입으로 검색
}
