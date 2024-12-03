//package org.zerock.cleanaido_customer_back.product.repository.search;
//
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.JPQLQuery;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
//import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
//import org.zerock.cleanaido_customer_back.customer.entity.QCustomer;
//import org.zerock.cleanaido_customer_back.product.dto.ReviewListDTO;
//import org.zerock.cleanaido_customer_back.product.entity.QReview;
//import org.zerock.cleanaido_customer_back.product.entity.QReviewImage;
//import org.zerock.cleanaido_customer_back.product.entity.Review;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Log4j2
//public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {
//
//    public ReviewSearchImpl() {
//        super(Review.class);
//    }
//
//    @Override
//    public PageResponseDTO<ReviewListDTO> listByProduct(PageRequestDTO pageRequestDTO, Long pno) {
//
//        QReview review = QReview.review;
//        QCustomer customer = QCustomer.customer;
//        QReviewImage reviewImage = QReviewImage.reviewImage;
//
//        // 기본 리뷰 쿼리 설정
//        JPQLQuery<Review> query = from(review);
//        query.leftJoin(review.reviewImages, reviewImage).fetchJoin(); // fetchJoin으로 이미지 가져오기
//        query.leftJoin(review.customer, customer);
//        query.where(review.product.pno.eq(pno));
//        query.orderBy(review.reviewNumber.desc());
//
//        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());
//        getQuerydsl().applyPagination(pageable, query);
//
//        List<ReviewListDTO> dtoList = query.fetch().stream()
//                .map(r -> ReviewListDTO.builder()
//                        .reviewNumber(r.getReviewNumber())
//                        .reviewContent(r.getReviewContent())
//                        .createDate(r.getCreateDate())
//                        .score(r.getScore())
//                        .customerName(r.getCustomer().getCustomerName())
//                        .fileNames(r.getReviewImages().stream()
//                                .map(image -> image.getFileName())
//                                .collect(Collectors.toList()))
//                        .customerProfileImg(r.getCustomer().getProfileImageUrl())
//                        .build())
//                .collect(Collectors.toList());
//
//        long total = query.fetchCount();
//
//        return PageResponseDTO.<ReviewListDTO>withAll()
//                .dtoList(dtoList)
//                .totalCount(total)
//                .pageRequestDTO(pageRequestDTO)
//                .build();
//    }
//}
