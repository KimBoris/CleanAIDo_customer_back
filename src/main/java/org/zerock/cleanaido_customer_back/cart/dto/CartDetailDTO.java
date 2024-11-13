package org.zerock.cleanaido_customer_back.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.cart.entity.Cart;
import org.zerock.cleanaido_customer_back.product.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDetailDTO {

    private Long cdno;
    private Cart cart;
    private Product product;
//    private String fileName;
    private int quantity;
}
