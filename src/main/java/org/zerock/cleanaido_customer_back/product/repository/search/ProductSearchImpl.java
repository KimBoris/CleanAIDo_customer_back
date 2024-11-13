package org.zerock.cleanaido_customer_back.product.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.entity.QCategory;
import org.zerock.cleanaido_customer_back.product.entity.QImageFiles;
import org.zerock.cleanaido_customer_back.product.entity.QProduct;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        QProduct product = QProduct.product;
        QImageFiles imageFiles = QImageFiles.imageFiles;
        QCategory category = QCategory.category;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.imageFiles, imageFiles).on(imageFiles.ord.eq(0));
        query.leftJoin(product.category, category);

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
                                imageFiles.filename.as("filename"),
                                category.cname.as("category")

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


    @Override
    public Page<Product> searchBy(String type, String keyword, Pageable pageable) {

        QProduct product = QProduct.product;
        QCategory category = QCategory.category;
        log.info("-----------------");
        log.info("Search Start." + keyword);

        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건 분기 처리

        if (keyword != null && !keyword.isEmpty()) {
            builder.or(product.pname.containsIgnoreCase(keyword));
            log.info(builder);
            builder.or(product.ptags.containsIgnoreCase(keyword));
            log.info(builder);
                builder.or(category.cname.containsIgnoreCase(keyword));
            log.info(builder);

        }

        log.info("-----------------------");
        log.info(builder);

        JPQLQuery<Product> query = from(product)
                .leftJoin(product.category, category)
                .where(builder);


        getQuerydsl().applyPagination(pageable, query);

        List<Product> results = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

}
