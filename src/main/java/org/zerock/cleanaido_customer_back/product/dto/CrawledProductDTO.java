package org.zerock.cleanaido_customer_back.product.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CrawledProductDTO {
    private String pname;
    private String pcode;
    private int price;
    private List<Long> categories;
    private String thumbnailUrl;
    private List<String> detailUrls;
}
