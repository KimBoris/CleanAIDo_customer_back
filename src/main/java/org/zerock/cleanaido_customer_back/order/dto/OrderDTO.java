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
    private long customerId;
    private String phoneNumber;
    private String deliveryAddress;
    private String deliveryMessage;
    private int totalPrice;
    private LocalDateTime orderDate;
    private String orderStatus;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO(Order order) {
        this.orderNumber = order.getOrderNumber();
        this.customerId = order.getCustomer().getCustomerId();
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
