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
public class ReviewListDTO {

    private Long reviewNumber;
    private String reviewContent;
    private LocalDateTime createdDate;
    private int score;
    private String fileName;
    private String customerName;

}
