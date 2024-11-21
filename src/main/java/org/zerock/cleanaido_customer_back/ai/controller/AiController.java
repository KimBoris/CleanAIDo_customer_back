package org.zerock.cleanaido_customer_back.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.cleanaido_customer_back.ai.service.AIService;
import org.zerock.cleanaido_customer_back.common.dto.UploadDTO;

@RestController
@Log4j2
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
public class AiController {

    private final AIService aiService;

    @GetMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object getAiAnswer(
            @ModelAttribute String customerText,
            @RequestParam("files") MultipartFile[] files
    ) {

        UploadDTO uploadDTO = new UploadDTO(files, null);
        return null;
    }

    @GetMapping(value = "solution")
    public Object getSolution(
            @RequestParam String imageTitle

    ) throws Exception {
        log.info("---------------------");
        log.info("imageTitle: "+imageTitle);
        String[] extractedImages = aiService.getImagelist(imageTitle);

        String extractedCategory = aiService.getCategory(extractedImages);

        String question = extractedCategory + " 모기를 잡고 싶어";

        return aiService.getSolution(question);
    }
}
