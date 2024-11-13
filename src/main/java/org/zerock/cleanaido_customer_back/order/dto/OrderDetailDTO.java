package org.zerock.cleanaido_customer_back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.order.entity.OrderDetail;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    private Long orderDetailId;
    private Long productId;
    private String productName;
    private int quantity;
    private int price;

    public OrderDetailDTO(OrderDetail orderDetail) {
        this.orderDetailId = orderDetail.getOrderDetailId();
        this.productId = orderDetail.getProduct().getPno();
        this.productName = orderDetail.getProduct().getPname();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
    }
}
