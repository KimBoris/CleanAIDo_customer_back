package org.zerock.cleanaido_customer_back.product.dto;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zerock.cleanaido_customer_back.category.entity.QCategory;
import org.zerock.cleanaido_customer_back.category.entity.QProductCategory;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.entity.QImageFile;
import org.zerock.cleanaido_customer_back.product.entity.QProduct;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReadDTO {

    private Long pno;
    private String pname;
    private int price;
    private String pstatus;
    private List<String> fileName;
    private int quantity;
}
