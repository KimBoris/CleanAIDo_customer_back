package org.zerock.cleanaido_customer_back.product.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;
import org.zerock.cleanaido_customer_back.product.entity.ImageFiles;
import org.zerock.cleanaido_customer_back.product.entity.Product;
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
        Product product = productRepository.getProduct(pno);

        if (product == null) {
            throw new EntityNotFoundException("게시물을 찾을 수 없습니다." + pno);
        }

        List<String> fileNames = product.getImageFiles().stream()
                .map(ImageFiles::getFileName)
                .collect(Collectors.toList());

        return ProductReadDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .price(product.getPrice())
                .pstatus(product.getPstatus())
                .filename(fileNames)
                .build();
    }

    public PageResponseDTO<ProductListDTO> search(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getSearchDTO().getSearchType();
        String keyword = pageRequestDTO.getSearchDTO().getKeyword();
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        Page<Product> resultPage = productRepository.searchBy(type, keyword, pageable);

        List<ProductListDTO> dtoList = resultPage.getContent().stream()
                .map(product -> ProductListDTO.builder()
                        .pno(product.getPno())
                        .pname(product.getPname())
                        .price(product.getPrice())
                        .pstatus(product.getPstatus())
                        .build()).collect(Collectors.toList());

        return new PageResponseDTO<>(dtoList, pageRequestDTO, resultPage.getTotalElements());
    }

}
