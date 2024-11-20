package org.zerock.cleanaido_customer_back.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewListDTO {

    private Long reviewNumber;
    private String reviewContent;
    private LocalDateTime createDate;
    private int score;
    private List<String> fileNames;
    private String customerName;
    private String customerProfileImg;

}
