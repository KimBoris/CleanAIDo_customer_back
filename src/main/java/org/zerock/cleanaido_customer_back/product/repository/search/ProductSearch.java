package org.zerock.cleanaido_customer_back.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductListDTO> searchBy(String type, String keyword, PageRequestDTO pageRequestDTO);




}
