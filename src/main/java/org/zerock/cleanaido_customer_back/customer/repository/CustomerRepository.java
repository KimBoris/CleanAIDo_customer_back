package org.zerock.cleanaido_customer_back.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
