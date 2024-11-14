package org.zerock.cleanaido_customer_back.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "product")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_number", unique = true, nullable = false)
    private Long reviewNumber;

    @Column(name = "category_name", nullable = false, length = 1000)
    private String reviewContent;

    @Column(name = "delFleg")
    private boolean delFlag;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updateDate;

    @Column(name = "score")
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
