package org.zerock.cleanaido_customer_back.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListDTO {
    private int pno;
    private String pcode;
    private String pname;
    private int price;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime releasedAt;
    private String pstatus;
}
