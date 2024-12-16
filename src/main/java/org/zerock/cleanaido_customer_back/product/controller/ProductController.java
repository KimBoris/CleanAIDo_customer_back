package org.zerock.cleanaido_customer_back.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.cart.service.CartService;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.common.dto.SearchDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;
//import org.zerock.cleanaido_customer_back.product.service.DaisoCrawlService;
import org.zerock.cleanaido_customer_back.product.service.ProductService;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
//    private final DaisoCrawlService daisoCrawlService;
    private final CartService cartService;

    // 상품 목록 조회 (검색 포함)
    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ProductListDTO>> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        SearchDTO searchDTO = SearchDTO.builder()
                .type(type)
                .keyword(keyword)
                .build();

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .searchDTO(searchDTO)
                .build();

        if (searchDTO.getKeyword() == null || searchDTO.getKeyword().isEmpty()) {
            log.info("Fetching full product list...");
            return ResponseEntity.ok(productService.listProduct(pageRequestDTO));
        } else {
            log.info("Searching with type: " + searchDTO.getType() + ", keyword: " + searchDTO.getKeyword());
            return ResponseEntity.ok(productService.search(pageRequestDTO));
        }
    }

    // 상품 상세 조회
    @GetMapping("read/{pno}")
    public ResponseEntity<ProductReadDTO> read(@PathVariable Long pno) {
        ProductReadDTO readDTO = productService.readProduct(pno);
        return ResponseEntity.ok(readDTO);
    }


    // 크롤링 데이터 저장
//    @PostMapping("crawl")
//    public ResponseEntity<String> crawlAndSaveProducts(@RequestParam String keyword) {
//        log.info("Starting crawl for keyword: " + keyword);
//        daisoCrawlService.crawlAndSaveProducts(keyword);
//        return ResponseEntity.ok("Crawling completed and products saved.");
//    }

    @PostMapping("")
    public ResponseEntity<Long> register(
            @RequestParam Long pno,
            @RequestParam int qty
    ) {
        Long productNumber = cartService.addCartDetail(pno, qty);
        return ResponseEntity.ok(productNumber);
    }

    // 추천상품(랜덤)
    @GetMapping("/suggest")
    public ResponseEntity<List<ProductListDTO>> suggestList(){

        return ResponseEntity.ok(productService.listProductSuggest());

    }
}
