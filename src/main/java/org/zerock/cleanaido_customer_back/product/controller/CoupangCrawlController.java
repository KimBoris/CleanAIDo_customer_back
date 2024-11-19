package org.zerock.cleanaido_customer_back.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.product.service.CoupangCrawlService;

@RestController
@RequestMapping("/api/v1/crawl")
@RequiredArgsConstructor
public class CoupangCrawlController {

    private final CoupangCrawlService crawlService;

    @PostMapping("/search")
    public ResponseEntity<String> searchAndSaveProducts(@RequestParam String keyword) {
        crawlService.crawlAndSaveProducts(keyword);
        return ResponseEntity.ok("Products have been crawled and saved.");
    }
}

