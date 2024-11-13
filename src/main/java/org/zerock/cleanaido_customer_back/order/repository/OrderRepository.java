package org.zerock.cleanaido_customer_back.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.order.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
}
