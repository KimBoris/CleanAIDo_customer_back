package org.zerock.cleanaido_customer_back.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.common.dto.UploadDTO;
import org.zerock.cleanaido_customer_back.common.util.CustomFileUtil;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;
import org.zerock.cleanaido_customer_back.product.dto.ReviewListDTO;
import org.zerock.cleanaido_customer_back.product.dto.ReviewRegisterDTO;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.entity.Review;
import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;
import org.zerock.cleanaido_customer_back.product.repository.ReviewRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomFileUtil customFileUtil;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public PageResponseDTO<ReviewListDTO> listReviews(PageRequestDTO pageRequestDTO, Long pno) {

        if (pageRequestDTO.getPage() < 1) {
            throw new IllegalArgumentException("페이지 번호는 1이상 이어야 합니다.");
        }

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());
        PageResponseDTO<ReviewListDTO> response = reviewRepository.list(pageRequestDTO, pno);

        return response;

    }

    public Long registerReview(ReviewRegisterDTO dto, UploadDTO uploadDTO) {

        // Product, Customer 엔티티 조회
        Product product = productRepository.findById(dto.getProductNumber())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductNumber()));
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + dto.getCustomerId()));

        // Review 필드
        Review review = Review.builder()
                .reviewContent(dto.getReviewContent())
                .product(product)
                .customer(customer)
                .build();

        // 파일 업로드 처리
        List<String> fileNames = Optional.ofNullable(uploadDTO.getFiles())
                .map(files -> Arrays.stream(files)
                        .filter(file -> !file.isEmpty()) // 실제 파일이 있는 경우만 필터링
                        .collect(Collectors.toList()))
                .filter(validFiles -> !validFiles.isEmpty()) // 빈 리스트는 제외
                .map(customFileUtil::saveFiles) // 유효한 파일이 있으면 저장
                .orElse(Collections.emptyList()); // 유효한 파일이 없으면 빈 리스트

        for (String fileName : fileNames) {
            review.addReviewImage(fileName);
        }

        reviewRepository.save(review);

        return review.getReviewNumber();

    }
}
