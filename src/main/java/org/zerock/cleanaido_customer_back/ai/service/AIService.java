package org.zerock.cleanaido_customer_back.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.zerock.cleanaido_customer_back.ai.repository.AIRepository;

import java.util.Map;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class AIService {

    private final AIRepository aiRepository;

    public String[] getImagelist(String imageTitle) throws Exception {

        log.info("---service start---");

        String pythonServerUrl = "http://127.0.0.1:8000/get-images";

        // 쿼리 파라미터에 포함될 데이터 설정
        String url = pythonServerUrl + "?file_name=" + imageTitle;

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
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 문자열을 String[][]로 변환
            String[][] extractedImages = objectMapper.readValue(responseBody, String[][].class);
            return extractedImages[0];
        } else {
            throw new RuntimeException("Failed to call Python server");
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
}
