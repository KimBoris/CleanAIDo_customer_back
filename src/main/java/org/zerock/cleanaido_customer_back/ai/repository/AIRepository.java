package org.zerock.cleanaido_customer_back.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.cleanaido_customer_back.ai.repository.search.AISearch;
import org.zerock.cleanaido_customer_back.category.entity.Category;


public interface AIRepository extends JpaRepository<Category, Long>, AISearch {
}
