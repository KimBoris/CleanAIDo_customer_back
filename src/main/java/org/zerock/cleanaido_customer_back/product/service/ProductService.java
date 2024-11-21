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
import org.zerock.cleanaido_customer_back.product.entity.ImageFile;
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
        // 기본 상품 데이터 가져오기
        ProductReadDTO productReadDTO = productRepository.getProduct(pno);

        if (productReadDTO == null) {
            throw new EntityNotFoundException("상품을 찾을 수 없습니다. pno: " + pno);
        }

        // 상품의 이미지 파일 리스트 가져오기
        List<String> fileNames = productRepository.findById(pno)
                .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. pno: " + pno))
                .getImageFiles().stream()
                .map(ImageFile::getFileName) // 이미지 URL만 가져오기
                .collect(Collectors.toList());

        // DTO에 이미지 리스트 설정
        productReadDTO.setFileName(fileNames);

        return productReadDTO;
    }

    public PageResponseDTO<ProductListDTO> search(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getSearchDTO().getSearchType();
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
                        .reviewCount(product.getReviewCount())
                        .build()).collect(Collectors.toList());

        log.info("-0-0-0-0-0-0-0-0-0-0");
        log.info(pageable);
        log.info(dtoList);
        return new PageResponseDTO<>(dtoList, pageRequestDTO, resultPage.getTotalPage());
    }

//    public PageResponseDTO<ProductListDTO> searchCategory(PageRequestDTO pageRequestDTO) {
//        String type = pageRequestDTO.getSearchDTO().getSearchType();
//        String keyword = pageRequestDTO.getSearchDTO().getKeyword();
//
//        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());
//
//        PageResponseDTO<ProductListDTO> resultPage = productRepository.searchByCategory(type, keyword,pageRequestDTO);
//
//        List<ProductListDTO> dtoList = resultPage.getDtoList().stream()
////                .filter(product-> product.getCategory() != null)
//                .map(product -> ProductListDTO.builder()
//                        .pno(product.getPno())
//                        .pname(product.getPname())
//                        .price(product.getPrice())
//                        .pstatus(product.getPstatus())
//                        .build()).collect(Collectors.toList());
//
//        log.info("-0-0-0-0-0-0-0-0-0-0");
//        log.info(pageable);
//        log.info(dtoList);
//        return new PageResponseDTO<>(dtoList, pageRequestDTO, resultPage.getTotalPage());
//    }

}
