package org.zerock.cleanaido_customer_back.product.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.repository.search.ProductSearch;

public interface ProductRepository
        extends JpaRepository<Product, Long>, ProductSearch {


    @Query("""
      select 
       new org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO(
        p, count(distinct (r)),  avg(distinct (r.score))
       )
      from Product p left join Review r on r.product = p     
      where p.pno = :pno 
      group by p 
           """)
    ProductReadDTO getProduct(@Param("pno") Long pno);


    boolean existsByPcode(String pcode);
}
