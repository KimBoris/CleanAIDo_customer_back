package org.zerock.cleanaido_customer_back.product.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;
import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        log.info(response);
        return response;
    }

    public ProductReadDTO readProduct(Long pno) {

        ProductReadDTO productReadDTO = productRepository.getProduct(pno);

        if (productReadDTO == null) {
            log.info("no product--------------------------");
            throw new EntityNotFoundException("상품을 찾을 수 없습니다. pno: " + pno);
        }

        return productReadDTO;
    }

    public PageResponseDTO<ProductListDTO> search(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getSearchDTO().getType();
        String keyword = pageRequestDTO.getSearchDTO().getKeyword();

        log.info("=========================================");
        log.info(type);

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        PageResponseDTO<ProductListDTO> resultPage = productRepository.searchBy(type, keyword,pageRequestDTO);

        List<ProductListDTO> dtoList = resultPage.getDtoList().stream()
                .map(product -> ProductListDTO.builder()
                        .pno(product.getPno())
                        .pname(product.getPname())
                        .price(product.getPrice())
                        .pstatus(product.getPstatus())
                        .fileName(product.getFileName())
                        .score(product.getScore())
                        .reviewCount(product.getReviewCount())
                        .category(product.getCategory())
                        .build()).collect(Collectors.toList());


        log.info(pageable);
        log.info(dtoList);
        return new PageResponseDTO<>(dtoList, pageRequestDTO, resultPage.getTotalPage());
    }

    // 추천상품 랜덤
    public List<ProductListDTO> listProductSuggest() {

        List<ProductListDTO> response = productRepository.listSuggest();

        return response;
    }

    // 자주 구매한 상품
    public PageResponseDTO<ProductListDTO> listFreqProduct(String customerId, PageRequestDTO pageRequestDTO) {

        if (pageRequestDTO.getPage() < 1) {
            throw new IllegalArgumentException("페이지 번호는 1이상 이어야 합니다.");
        }

        PageResponseDTO<ProductListDTO> response = productRepository.listFreq(customerId, pageRequestDTO);

        return response;

    }


}
