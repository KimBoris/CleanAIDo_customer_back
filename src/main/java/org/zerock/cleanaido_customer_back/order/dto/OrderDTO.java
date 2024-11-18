package org.zerock.cleanaido_customer_back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderNumber;
    private String customerId; // 기존 고객 ID
    private String customerName; // 추가된 고객 이름
    private String phoneNumber;
    private String deliveryAddress;
    private String deliveryMessage;
    private int totalPrice;
    private LocalDateTime orderDate;
    private String orderStatus;
    private List<OrderDetailDTO> orderDetails;

    // 수정된 생성자
    public OrderDTO(Order order) {
        this.orderNumber = order.getOrderNumber();
        this.customerId = order.getCustomer().getCustomerId(); // 고객 ID
        this.customerName = order.getCustomer().getCustomerName(); // 고객 이름 가져오기
        this.phoneNumber = order.getPhoneNumber();
        this.deliveryAddress = order.getDeliveryAddress();
        this.deliveryMessage = order.getDeliveryMessage();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getOrderStatus();
        this.orderDetails = order.getOrderDetails().stream()
                .map(OrderDetailDTO::new)
                .collect(Collectors.toList());
    }
}

