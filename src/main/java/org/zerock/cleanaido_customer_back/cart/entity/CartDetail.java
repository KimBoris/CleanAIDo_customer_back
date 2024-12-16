package org.zerock.cleanaido_customer_back.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.cleanaido_customer_back.product.entity.Product;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "cart_detail")
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cdno")
    private Long cdno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_no", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_number", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
