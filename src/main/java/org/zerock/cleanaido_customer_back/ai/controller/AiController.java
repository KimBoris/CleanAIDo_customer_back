package org.zerock.cleanaido_customer_back.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.cleanaido_customer_back.ai.dto.SolutionDTO;
import org.zerock.cleanaido_customer_back.ai.service.AIService;
import org.zerock.cleanaido_customer_back.common.dto.TempUploadDTO;
import org.zerock.cleanaido_customer_back.common.dto.UploadDTO;

@RestController
@Log4j2
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class AiController {

    private final AIService aiService;


    @PostMapping(value = "solution", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object getSolution(
            @RequestParam MultipartFile imageFile,
            @RequestParam String customerText
    ) throws Exception {
        log.info("----------------getSolution Start");

        TempUploadDTO tempUploadDTO = new TempUploadDTO(imageFile, null);
        String imageTitle = aiService.registImg(tempUploadDTO);

        log.info("---------------------");
        log.info("imageTitle: "+imageTitle);
        
        //유사 이미지 추출
        String[] extractedImages = aiService.getImagelist(imageTitle);

        //카테고리 추출
        String extractedCategory = aiService.getCategory(extractedImages);
        
        String question = extractedCategory + customerText;

        //지피티에게 답변받기
        aiService.deleteTempImg(imageTitle);

        String solution = aiService.getSolution(question);

        return new SolutionDTO(extractedCategory, solution);
    }
}
