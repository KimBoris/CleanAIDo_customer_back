package org.zerock.cleanaido_customer_back.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.product.entity.Review;
import org.zerock.cleanaido_customer_back.product.repository.search.ReviewSearch;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewSearch {
}
