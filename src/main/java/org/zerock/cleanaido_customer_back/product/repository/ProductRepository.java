package org.zerock.cleanaido_customer_back.product.repository;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.repository.search.ProductSearch;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>, ProductSearch {

    @Query("SELECT p FROM Product p left join fetch p.imageFiles where p.pno = :pno")
    Product getProduct(@Param("pno") Long pno);

}
