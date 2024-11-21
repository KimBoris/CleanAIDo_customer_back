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

    @EntityGraph(attributePaths = "imageFiles")
    @Query("""
            SELECT new org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO(
                 p.pno, p.pname, p.price, p.pstatus, avg(r.score), count(r)
             )
            FROM Product p
            LEFT JOIN Review r on r.product = p
            WHERE p.pno = :pno
            GROUP BY p
           """)
    ProductReadDTO getProduct(@Param("pno") Long pno);

    boolean existsByPcode(String pcode);
}
