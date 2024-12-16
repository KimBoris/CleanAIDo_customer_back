package org.zerock.cleanaido_customer_back.category.repository.search;

import org.zerock.cleanaido_customer_back.category.dto.CategoryListDTO;
import org.zerock.cleanaido_customer_back.category.entity.Category;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;

import java.util.List;

public interface CategorySearch {
    public List<CategoryListDTO> getCategoryList();
}
