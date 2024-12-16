package org.zerock.cleanaido_customer_back.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.cart.entity.Cart;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomer(Customer customer);
}
