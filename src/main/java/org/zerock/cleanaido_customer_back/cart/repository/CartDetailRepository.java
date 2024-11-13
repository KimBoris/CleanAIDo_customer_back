package org.zerock.cleanaido_customer_back.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.cart.entity.CartDetail;
import org.zerock.cleanaido_customer_back.cart.repository.search.CartDetailSearch;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long>, CartDetailSearch {


}
