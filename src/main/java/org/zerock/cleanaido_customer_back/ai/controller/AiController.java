package org.zerock.cleanaido_customer_back.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

//    @GetMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Object getAiAnswer(
//            @ModelAttribute String customerText,
//            @RequestParam("files") MultipartFile[] files
//    ) {
//
//        UploadDTO uploadDTO = new UploadDTO(files, null);
//        return null;
//    }

    @GetMapping(value = "solution", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object getSolution(
            @RequestParam MultipartFile imageFile,
            @ModelAttribute String customerText
    ) throws Exception {

        TempUploadDTO tempUploadDTO = new TempUploadDTO(imageFile, null);
        String imageTitle = aiService.registImg(tempUploadDTO);

        log.info("---------------------");
        log.info("imageTitle: "+imageTitle);
        String[] extractedImages = aiService.getImagelist(imageTitle);

        String extractedCategory = aiService.getCategory(extractedImages);

        String question = extractedCategory + customerText;

        aiService.deleteTempImg(imageTitle);

        return aiService.getSolution(question);
    }
}
