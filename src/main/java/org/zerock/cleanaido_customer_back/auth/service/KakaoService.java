package org.zerock.cleanaido_customer_back.auth.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zerock.cleanaido_customer_back.customer.dto.KakaoUserDTO;

import java.util.Map;

@Service
public class KakaoService {

    public String getAccessToken(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", "bd7725f821010811fbfbb131b8f9985d");
            params.add("redirect_uri", "http://www.cleanaido.shop/oauth/kakao/callback");
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://kauth.kakao.com/oauth/token", request, Map.class);

            if (response.getStatusCode() != HttpStatus.OK || !response.getBody().containsKey("access_token")) {
                throw new IllegalArgumentException("Failed to retrieve access token from Kakao: " + response.getBody());
            }

            return (String) response.getBody().get("access_token");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error during Kakao access token retrieval: " + e.getMessage(), e);
        }
    }

    public KakaoUserDTO getUserInfo(String accessToken) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new IllegalArgumentException("Failed to retrieve user info from Kakao: " + response.getBody());
            }

            Map<String, Object> kakaoAccount = (Map<String, Object>) response.getBody().get("kakao_account");
            String id = String.valueOf(response.getBody().get("id"));
            String email = (kakaoAccount != null && kakaoAccount.get("email") != null)
                    ? kakaoAccount.get("email").toString() : null;
            String nickname = (response.getBody().get("properties") != null)
                    ? (String) ((Map<String, Object>) response.getBody().get("properties")).get("nickname")
                    : "Unknown";

            if (email == null || email.isBlank()) {
                System.out.println("Warning: Email is not available in Kakao account. Using ID instead.");
                email = id + "@kakao-temp.com"; // 임시 이메일 생성
            }

            return new KakaoUserDTO(id, nickname, email, null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error during Kakao user info retrieval: " + e.getMessage(), e);
        }
    }


}
