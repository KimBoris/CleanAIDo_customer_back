package org.zerock.cleanaido_customer_back.category.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.category.dto.CategoryListDTO;
import org.zerock.cleanaido_customer_back.category.repository.CategoryRepository;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryListDTO> getCategoryList() {
        // Repository를 호출하여 전체 데이터를 가져옴
        return categoryRepository.getCategoryList();
    }
}
