package org.zerock.cleanaido_customer_back.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.cleanaido_customer_back.category.entity.Category;
import org.zerock.cleanaido_customer_back.category.repository.search.CategorySearch;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategorySearch {

    @Query("SELECT c FROM Category c WHERE c.cname = :name")
    List<Category> findByName(@Param("name") String name);

    @Query("SELECT c FROM Category c WHERE c.cname IN :names")
    List<Category> findByNames(@Param("names") List<String> names);

}
