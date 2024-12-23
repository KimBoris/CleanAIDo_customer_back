package org.zerock.cleanaido_customer_back.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.category.dto.CategoryListDTO;
import org.zerock.cleanaido_customer_back.category.service.CategoryService;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.common.dto.SearchDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;


    //카테고리 리스트
    @GetMapping("list")
    public ResponseEntity<List<CategoryListDTO>> list() {
        // 페이지네이션 없이 전체 카테고리 리스트를 반환
        List<CategoryListDTO> categoryList = categoryService.getCategoryList();
        log.info("카테고리 목록 조회: {}", categoryList);

        return ResponseEntity.ok(categoryList);
    }

}
