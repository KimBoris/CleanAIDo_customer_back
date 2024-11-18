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

    public OrderDTO placeOrder(PurchaseDTO purchaseDTO) {
        // 고객 ID로 조회
        Customer customer = customerRepository.findByCustomerId(purchaseDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // 주문 생성
        Order order = Order.builder()
                .customer(customer) // 고객 정보 설정
                .phoneNumber(purchaseDTO.getPhoneNumber())
                .deliveryAddress(purchaseDTO.getDeliveryAddress())
                .deliveryMessage(purchaseDTO.getDeliveryMessage())
                .orderDate(LocalDateTime.now())
                .orderStatus("주문 완료")
                .totalPrice(purchaseDTO.getTotalPrice())
                .build();

        // 주문 상세 정보 추가
        purchaseDTO.getOrderDetails().forEach(detailDTO -> {
            OrderDetail orderDetail = OrderDetail.builder()
                    .product(Product.builder().pno(detailDTO.getProductId()).build())
                    .quantity(detailDTO.getQuantity())
                    .price(detailDTO.getPrice())
                    .build();
            order.addOrderDetail(orderDetail);
        });

        // 주문 저장
        Order savedOrder = orderRepository.save(order);

        // 고객 이름 포함하여 반환
        OrderDTO orderDTO = new OrderDTO(savedOrder);
        orderDTO.setCustomerName(customer.getCustomerName()); // 고객 이름 추가
        return orderDTO;
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
