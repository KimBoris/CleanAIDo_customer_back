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
//
//@SpringBootTest
//@Log4j2
//@RequiredArgsConstructor
//public class ReviewTests {
//
//    @Autowired
//    private ReviewRepository reviewRepository;d
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
//            /*
//
//            @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "review_number", unique = true, nullable = false)
//    private Long reviewNumber;
//
//    @Column(name = "review_content", nullable = false, length = 1000)
//    private String reviewContent;
//
//    @Column(name = "delFleg")
//    @Builder.Default
//    private boolean delFlag = false;
//
//    @CreationTimestamp
//    @Column(name = "create_date", updatable = false)
//    private LocalDateTime createDate;
//
//    @UpdateTimestamp
//    @Column(name = "updated_date")
//    private LocalDateTime updateDate;
//
//    @Column(name = "score", nullable = false)
//    private int score;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Product product;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Customer customer;
//
//
//             */
//
//        }
//
//    }
//
//}
