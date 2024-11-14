package org.zerock.cleanaido_customer_back.product.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.customer.entity.QCustomer;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.dto.ReviewListDTO;
import org.zerock.cleanaido_customer_back.product.entity.QReview;
import org.zerock.cleanaido_customer_back.product.entity.Review;

import java.util.List;

@Log4j2
public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(Review.class);
    }

    @Override
    public PageResponseDTO<ReviewListDTO> list(PageRequestDTO pageRequestDTO, Long pno) {

        QReview review = QReview.review;
        QCustomer customer = QCustomer.customer;

        JPQLQuery<Review> query = from(review);
        query.leftJoin(customer, customer).on(review.customer.customerName.eq(customer.customerName));
        query.where(review.product.pno.eq(pno));
        query.orderBy(review.reviewNumber.desc());

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize());

        getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ReviewListDTO> results =
                query.select(
                        Projections.bean(
                                ReviewListDTO.class,
                                review.reviewNumber,
                                review.reviewContent,
                                review.createDate,
                                review.score,
                                customer.customerName
                        )
                );
        List<ReviewListDTO> dtoList = results.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<ReviewListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

    }
}
