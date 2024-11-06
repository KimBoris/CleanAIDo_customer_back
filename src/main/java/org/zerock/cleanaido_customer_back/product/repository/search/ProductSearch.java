package org.zerock.cleanaido_customer_back.product.repository.search;

import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);


}
