package org.zerock.cleanaido_customer_back.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.product.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
