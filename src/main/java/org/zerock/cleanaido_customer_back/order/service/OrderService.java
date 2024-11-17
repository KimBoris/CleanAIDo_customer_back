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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    // 주문 생성 메서드
    public OrderDTO placeOrder(PurchaseDTO purchaseDTO) {
        Customer customer = customerRepository.findByCustomerId(purchaseDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + purchaseDTO.getCustomerId()));

        // 프론트에서 계산된 총 가격 사용
        Order order = Order.builder()
                .customer(customer)
                .phoneNumber(purchaseDTO.getPhoneNumber())
                .deliveryAddress(purchaseDTO.getDeliveryAddress())
                .deliveryMessage(purchaseDTO.getDeliveryMessage())
                .orderDate(LocalDateTime.now())
                .orderStatus("주문 완료")
                .totalPrice(purchaseDTO.getTotalPrice())  // 프론트에서 전달된 총 가격 사용
                .build();

        // 주문 상세 정보 추가
        purchaseDTO.getOrderDetails().forEach(detailDTO -> {
            OrderDetail orderDetail = OrderDetail.builder()
                    .product(Product.builder().pno(detailDTO.getProductId()).build()) // Product 객체를 생성하고 pno 설정
                    .quantity(detailDTO.getQuantity())
                    .price(detailDTO.getPrice())  // 프론트에서 전달된 개별 상품 가격 사용
                    .build();

            order.addOrderDetail(orderDetail);
        });

        Order savedOrder = orderRepository.save(order);
        return new OrderDTO(savedOrder);
    }

    // 고객의 주문 목록을 조회하는 메서드
    public List<OrderDTO> getCustomerOrders(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        return orderRepository.findByCustomer(customer)
                .stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }

    // 주문 상태를 업데이트하는 메서드
    public void updateOrderStatus(Long orderNumber, String status) {
        Order order = orderRepository.findById(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderNumber));

        order.setOrderStatus(status);
        orderRepository.save(order);
    }
}
