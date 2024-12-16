package org.zerock.cleanaido_customer_back.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailDTO {
    private Long cdno;
    private Long cartNo;
    private int quantity;

    private Map<String, Object> product;

    public CartDetailDTO(Long cdno, Long cartNo, Long productNumber, String productName, int price, int quantity) {
        this.cdno = cdno;
        this.cartNo = cartNo;
        this.quantity = quantity;

        this.product = Map.of(
                "pno", productNumber,
                "pname", productName,
                "price", price
        );
    }
}
