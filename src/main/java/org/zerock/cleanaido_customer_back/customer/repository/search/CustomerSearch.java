package org.zerock.cleanaido_customer_back.customer.repository.search;

import org.springframework.data.domain.Pageable;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.customer.dto.CustomerListDTO;

public interface CustomerSearch {

    PageResponseDTO<CustomerListDTO> list(Pageable pageable);

}
