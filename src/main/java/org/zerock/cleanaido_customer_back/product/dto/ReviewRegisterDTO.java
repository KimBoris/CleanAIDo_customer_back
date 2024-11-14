package org.zerock.cleanaido_customer_back.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRegisterDTO {

    private Long productNumber;
    private String reviewContent;

}
