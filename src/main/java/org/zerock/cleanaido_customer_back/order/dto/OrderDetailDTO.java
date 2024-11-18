package org.zerock.cleanaido_customer_back.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.order.entity.OrderDetail;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
public class OrderDetailDTO {

    private Long orderDetailId;
    private Long productId;
    private String productName;
    private int quantity;
    private int price;
    private String productImage; // 상품 이미지 추가

    public OrderDetailDTO(OrderDetail orderDetail) {
        this.orderDetailId = orderDetail.getOrderDetailId();
        this.productId = orderDetail.getProduct().getPno();
        this.productName = orderDetail.getProduct().getPname();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
        this.productImage = orderDetail.getProduct().getImageFiles().stream()
                .findFirst() // 첫 번째 이미지 가져오기
                .map(imageFile -> imageFile.getFileName())
                .orElse("/images/default-product.png"); // 기본 이미지 경로
    }
}
