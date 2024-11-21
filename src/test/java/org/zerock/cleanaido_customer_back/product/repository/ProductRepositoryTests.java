package org.zerock.cleanaido_customer_back.product.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;

import java.util.Arrays;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    @Test
    public void testRead( ) {

        Long pno = 1L;

        ProductReadDTO readDTO = productRepository.getProduct(pno);

        log.info(readDTO.toString());


//        Object[] result = productRepository.getProduct(pno);
//
//        log.info("------------------");
//
//        log.info(Arrays.toString(result));
//
//        log.info("------------------");
//        Object[]  arr = (Object[]) result[0];
//
//        Product product = (Product) arr[0];
//        long count = (Long) arr[1];
//        log.info(product);
//        log.info(count);
//
//        log.info("=============================");
//        log.info(product.getImageFiles());

//        Product product = (Product) result[0][0];
//        long reviewCnt = (long) result[1];
//
//        log.info(product);
//        log.info(product.getImageFiles());
//
//        log.info(product.getImageFiles().size());
//
//        log.info("========================");

    }
}
