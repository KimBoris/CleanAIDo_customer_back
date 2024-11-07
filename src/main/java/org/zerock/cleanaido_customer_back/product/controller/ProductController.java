package org.zerock.cleanaido_customer_back.product.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin(origins="http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    //    @CrossOrigin(origins = "http://localhost:5177")
    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .build();
        log.info("-----------------");
        log.info(page);
        log.info(size);

        return ResponseEntity.ok(productService.listProduct(pageRequestDTO));
    }
}
