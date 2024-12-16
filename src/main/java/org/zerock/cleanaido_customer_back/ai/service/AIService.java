package org.zerock.cleanaido_customer_back.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zerock.cleanaido_customer_back.ai.repository.AIRepository;
import org.zerock.cleanaido_customer_back.common.dto.TempUploadDTO;
import org.zerock.cleanaido_customer_back.common.util.CustomFileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class AIService {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    private final AIRepository aiRepository;
    private final CustomFileUtil customFileUtil;

    public String[] getImagelist(String imageTitle) throws Exception {

        log.info("---service start---");

        File file = new File(uploadPath +"\\"+ imageTitle);

        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("Image file does not exist: " + file.getAbsolutePath());
        }

        String pythonServerUrl = "http://127.0.0.1:8000/get-images";

        // RestTemplate 초기화
        RestTemplate restTemplate = new RestTemplate();

        // 파일 데이터 읽기
        byte[] fileContent;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            fileContent = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }

        // Multipart 요청을 위한 데이터 구성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(fileContent) {
            @Override
            public String getFilename() {
                return file.getName(); // 파일 이름 설정
            }
        });

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        // POST 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(pythonServerUrl, HttpMethod.POST, entity, String.class);

        log.info(response.getBody());

        // 결과 반환
        if (response.getStatusCode() == HttpStatus.OK) {
            // JSON 문자열을 2차원 배열로 변환
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 문자열을 String[][]로 변환
            String[][] extractedImages = objectMapper.readValue(responseBody, String[][].class);
            return extractedImages[0];
        } else {
            throw new RuntimeException("Failed to call Python server: " + response.getStatusCode());
        }
    }


    public String getCategory(String[] imgName){

        return aiRepository.getCategorys(imgName);
    }

    public String getSolution(String question) throws Exception {

        log.info("---service start---");

        String pythonServerUrl = "http://127.0.0.1:8000/get-solution";

        // 쿼리 파라미터에 포함될 데이터 설정
        String url = pythonServerUrl + "?keywords=" + question;

        log.info(url);

        // RestTemplate 초기화
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정 (필요한 경우, GET 요청에서는 주로 필수 아님)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔티티 생성 (GET 요청에서는 보통 본문이 없지만 헤더는 설정)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // GET 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        log.info(response.getBody().getClass().getName());

        // 결과 반환
        if (response.getStatusCode() == HttpStatus.OK) {
            // JSON 문자열을 2차원 배열로 변환
            String responseBody = response.getBody();
            return responseBody;
        } else {
            throw new RuntimeException("Failed to call Python server");
        }

    }
    public String registImg(TempUploadDTO tempUploadDTO) {

        // 파일이 있는 경우에만 처리
        String imageFileName = Optional.ofNullable(tempUploadDTO.getFile())
                .filter(file -> !file.isEmpty()) // 실제 파일이 있는 경우만 처리
                .map(customFileUtil::saveFile) // 유효한 파일이 있으면 저장
                .orElse(null); // 파일이 없으면 null 반환

        return imageFileName; // 저장된 파일 이름 반환
    }

    public void deleteTempImg(String imageFileName) {
        customFileUtil.deleteFile(imageFileName);
    }
}
