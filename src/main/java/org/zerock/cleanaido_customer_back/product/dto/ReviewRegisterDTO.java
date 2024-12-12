package org.zerock.cleanaido_customer_back.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.product.entity.ReviewImage;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRegisterDTO {

    private Long productNumber;
    private String customerId;
    private String reviewContent;
    private List<String> fileNames;
    private int score;

}
