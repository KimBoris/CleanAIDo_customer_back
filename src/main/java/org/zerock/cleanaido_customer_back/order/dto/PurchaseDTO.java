package org.zerock.cleanaido_customer_back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
//    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String deliveryAddress;
    private String deliveryMessage;
    private int totalPrice;
    private List<OrderDetailDTO> orderDetails;
}