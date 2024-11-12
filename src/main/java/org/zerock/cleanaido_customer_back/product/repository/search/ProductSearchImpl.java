package org.zerock.cleanaido_customer_back.product.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.entity.QImageFiles;
import org.zerock.cleanaido_customer_back.product.entity.QProduct;

import java.util.List;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        QProduct product = QProduct.product;
        QImageFiles imageFiles = QImageFiles.imageFiles;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.imageFiles, imageFiles).on(imageFiles.ord.eq(0));

        query.orderBy(product.pno.desc());

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ProductListDTO> results =
                query.select(
                        Projections.bean(
                                ProductListDTO.class,
                                product.pno,
                                product.pname,
                                product.price,
                                product.pstatus,
                                imageFiles.fileName.as("fileName")
                        )
                );
        List<ProductListDTO> dtoList = results.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll().
                dtoList(dtoList).
                totalCount(total).
                pageRequestDTO(pageRequestDTO).
                build();

    }
}
