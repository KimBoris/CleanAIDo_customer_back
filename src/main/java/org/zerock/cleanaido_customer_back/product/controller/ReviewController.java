package org.zerock.cleanaido_customer_back.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ReviewListDTO>> list(
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
            @ModelAttribute ReviewRegisterDTO reviewRegisterDTO,
            @RequestParam("reviewContent") String reviewContent,
            @RequestParam("productNumber") Long productNumber,
            @RequestParam("customerId") String customerId,
            @RequestParam("files") MultipartFile[] files
            ) {

        UploadDTO uploadDTO = new UploadDTO(files, null);
        Long reviewNumber = reviewService.registerReview(reviewRegisterDTO, uploadDTO);

        return ResponseEntity.ok(reviewNumber);
    }

}
