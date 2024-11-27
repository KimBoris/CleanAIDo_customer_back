package org.zerock.cleanaido_customer_back.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void updateFcmToken(String customerId, String fcmToken) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found" + customerId));

        customer.setFcmToken(fcmToken);

    }


}
