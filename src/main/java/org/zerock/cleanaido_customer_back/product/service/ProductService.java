package org.zerock.cleanaido_customer_back.product.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    public PageResponseDTO<ProductListDTO> listProduct(PageRequestDTO pageRequestDTO) {

        if (pageRequestDTO.getPage() < 1) {
            throw new IllegalArgumentException("페이지 번호는 1이상 이어야 합니다.");
        }
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());
        PageResponseDTO<ProductListDTO> response = productRepository.list(pageRequestDTO);

        log.info("---------------------------------------1");

        return response;
    }

}
