package org.zerock.cleanaido_customer_back.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"product","category"})
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_number")
    private Long pcno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_number")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;


}
