package org.zerock.cleanaido_customer_back.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;
import org.zerock.cleanaido_customer_back.order.dto.OrderDTO;
import org.zerock.cleanaido_customer_back.order.dto.PurchaseDTO;
import org.zerock.cleanaido_customer_back.order.entity.Order;
import org.zerock.cleanaido_customer_back.order.entity.OrderDetail;
import org.zerock.cleanaido_customer_back.order.repository.OrderRepository;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderDTO placeOrder(PurchaseDTO purchaseDTO) {
        Customer customer = customerRepository.findByCustomerId(purchaseDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + purchaseDTO.getCustomerId()));

        Order order = Order.builder()
                .customer(customer)
                .phoneNumber(purchaseDTO.getPhoneNumber())
                .deliveryAddress(purchaseDTO.getDeliveryAddress())
                .deliveryMessage(purchaseDTO.getDeliveryMessage())
                .orderDate(LocalDateTime.now())
                .orderStatus("주문 완료")  // 문자열로 상태 설정
                .build();

        // 총 가격 계산
        int calculatedTotalPrice = purchaseDTO.getOrderDetails().stream().mapToInt(detailDTO -> {
            Product product = productRepository.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + detailDTO.getProductId()));

            OrderDetail orderDetail = OrderDetail.builder()
                    .product(product)
                    .quantity(detailDTO.getQuantity())
                    .price(product.getPrice())
                    .build();

            order.addOrderDetail(orderDetail);
            return orderDetail.getTotalPrice();
        }).sum();

        // 총 가격 설정
        order.setTotalPrice(calculatedTotalPrice);

        Order savedOrder = orderRepository.save(order);
        return new OrderDTO(savedOrder);
    }

    public List<OrderDTO> getCustomerOrders(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        return orderRepository.findByCustomer(customer)
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    public void updateOrderStatus(Long orderNumber, String status) {
        Order order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderNumber));

        // 상태 업데이트
        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}
