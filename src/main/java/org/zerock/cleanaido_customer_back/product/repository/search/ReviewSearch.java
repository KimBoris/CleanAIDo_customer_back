package org.zerock.cleanaido_customer_back.product.repository.search;

import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ReviewListDTO;

public interface ReviewSearch {

    PageResponseDTO<ReviewListDTO> list(PageRequestDTO pageRequestDTO, Long pno);

}
