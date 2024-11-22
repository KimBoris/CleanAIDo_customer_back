package org.zerock.cleanaido_customer_back.product.entity;

import jakarta.persistence.Column;
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
public class UsageImageFile {

    @Column(name ="ord")
    private int ord;

    @Column(name="file_name")
    private String fileName;
}
