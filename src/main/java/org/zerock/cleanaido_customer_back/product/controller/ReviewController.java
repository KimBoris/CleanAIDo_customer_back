package org.zerock.cleanaido_customer_back.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.common.dto.UploadDTO;
import org.zerock.cleanaido_customer_back.product.dto.ReviewListDTO;
import org.zerock.cleanaido_customer_back.product.dto.ReviewRegisterDTO;
import org.zerock.cleanaido_customer_back.product.service.ReviewService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@ToString
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("listbyproduct")
    public ResponseEntity<PageResponseDTO<ReviewListDTO>> listbyproduct(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "pno", required = false) long pno) {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .build();

        return ResponseEntity.ok(reviewService.listReviewsByProduct(pageRequestDTO, pno));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> register(
            ReviewRegisterDTO reviewRegisterDTO,
            @RequestParam(value = "files", required = false) MultipartFile[] files
    ) {

        String customerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("==============Review customerId===============");
        log.info("customerID : " + customerId);

        reviewRegisterDTO.setCustomerId(customerId);

        UploadDTO uploadDTO = new UploadDTO(files, null);
        Long reviewNumber = reviewService.registerReview(reviewRegisterDTO, uploadDTO);

        return ResponseEntity.ok(reviewNumber);
    }

}
