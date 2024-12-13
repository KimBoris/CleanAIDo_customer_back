//package org.zerock.cleanaido_customer_back.product;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//import org.zerock.cleanaido_customer_back.customer.entity.Customer;
//import org.zerock.cleanaido_customer_back.product.entity.Product;
//import org.zerock.cleanaido_customer_back.product.entity.Review;
//import org.zerock.cleanaido_customer_back.product.repository.ReviewRepository;
//import org.zerock.cleanaido_customer_back.product.service.ReviewService;
//
//@SpringBootTest
//@Log4j2
//@RequiredArgsConstructor
//public class ReviewTests {
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private ReviewService reviewService;
//
//    @Test
//    @Transactional
//    @Commit
//    public void testReviewRegister() {
//        for (int i = 1; i <= 10; i++) {
//            for (long j = 1; j <= 12; j++) {
//                Product product = Product.builder().pno(j).build();
//                Customer customer = Customer.builder().customerId("customer").build();
//                Review review = Review.builder()
//                        .customer(customer)
//                        .product(product)
//                        .reviewContent("사장님이 맛있고 음식이 착해요")
//                        .score(i)
//                        .build();
//                reviewRepository.save(review);
//            }
//
//        }
//
//    }
//
//    @Test
//    @Transactional
//    @Commit
//    public void testReviewDelete() {
//
//        reviewService.deleteReview(1L);
//    }
//
//}
