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
import org.zerock.cleanaido_customer_back.category.entity.QCategory;
import org.zerock.cleanaido_customer_back.category.entity.QProductCategory;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.entity.*;

import java.util.List;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        QProduct product = QProduct.product;
        QImageFile imageFile = QImageFile.imageFile;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.imageFiles, imageFile).on(imageFile.ord.eq(0));
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
                                imageFile.fileName
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
    public PageResponseDTO<ProductListDTO> searchBy(String type, String keyword, PageRequestDTO pageRequestDTO) {

        QProduct product = QProduct.product;
        QProductCategory productCategory = QProductCategory.productCategory;
        QCategory category = QCategory.category;
        QImageFile imageFile = QImageFile.imageFile;

        log.info("-----------------");
        log.info("Search Start." + keyword);

        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건 분기 처리


        log.info("-----------------------");
        log.info(builder);

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.imageFiles, imageFile).on(imageFile.ord.eq(0));
        query.leftJoin(productCategory).on(product.pno.eq(productCategory.product.pno));
        query.leftJoin(productCategory).on(category.cno.eq(productCategory.category.cno));
        query.orderBy(product.pno.desc());




        if (keyword != null && !keyword.isEmpty()) {
            builder.or(product.pname.containsIgnoreCase(keyword));
            builder.or(product.ptags.containsIgnoreCase(keyword));
            builder.or(productCategory.category.cname.containsIgnoreCase(keyword));
//                builder.or(category.cname.containsIgnoreCase(keyword));

        }


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
                                imageFile.fileName.as("fileName")
                        )
                );
        List<ProductListDTO> dtoList = results.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();


    }


    @Override
    public PageResponseDTO<ProductListDTO> searchByCategory(String type, String keyword, PageRequestDTO pageRequestDTO) {
        QProduct product = QProduct.product;
        QProductCategory productCategory = QProductCategory.productCategory;
        QImageFile imageFile = QImageFile.imageFile;
        QCategory category = QCategory.category;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.imageFiles, imageFile).on(imageFile.ord.eq(0));
        query.leftJoin(productCategory).on(product.pno.eq(productCategory.product.pno));
        query.leftJoin(category).on(productCategory.category.cno.eq(category.cno));
        query.where(category.cname.like("%" + keyword + "%"));
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
                                imageFile.fileName.as("fileName")
                        )
                );
        List<ProductListDTO> dtoList = results.fetch();


        long total = query.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
