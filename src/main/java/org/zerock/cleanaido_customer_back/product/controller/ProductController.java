package org.zerock.cleanaido_customer_back.product.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.service.ProductService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@ToString
public class ProductController {

    private final ProductService productService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> list() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        log.info("hello");

        return ResponseEntity.ok(productService.listProduct(pageRequestDTO));
    }
}
