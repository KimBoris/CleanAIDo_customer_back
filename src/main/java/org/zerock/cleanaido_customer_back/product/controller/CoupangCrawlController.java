package org.zerock.cleanaido_customer_back.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.product.service.CoupangCrawlService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/crawl")
@RequiredArgsConstructor
public class CoupangCrawlController {

    private final CoupangCrawlService crawlService;

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchProducts(@RequestParam String keyword) {
        List<Map<String, Object>> products = crawlService.crawlProducts(keyword);
        if (products.isEmpty()) {
            return ResponseEntity.status(500).body(List.of(Map.of("error", "No products found or failed to fetch.")));
        }
        return ResponseEntity.ok(products);
    }
}
