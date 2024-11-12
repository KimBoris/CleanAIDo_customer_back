package org.zerock.cleanaido_customer_back.product.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class UsageImageFiles {
    private int ord;
    private String filename;
}
