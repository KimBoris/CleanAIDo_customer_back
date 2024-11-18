package org.zerock.cleanaido_customer_back.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.product.service.CoupangCrawlService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crawl")
@RequiredArgsConstructor
public class CoupangCrawlController {

    private final CoupangCrawlService crawlService;

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchProducts(@RequestParam String keyword) {
        List<String> products = crawlService.searchProducts(keyword);
        if (products.isEmpty()) {
            return ResponseEntity.status(500).body(List.of("Failed to fetch products or no products found."));
        }
        return ResponseEntity.ok(products);
    }
}



