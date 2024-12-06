package org.zerock.cleanaido_customer_back.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.auth.util.JWTUtil;
import org.zerock.cleanaido_customer_back.customer.dto.CustomerRegisterDTO;
import org.zerock.cleanaido_customer_back.customer.dto.KakaoUserDTO;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.service.CustomerService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        if (code == null || code.isBlank()) {
            return ResponseEntity.badRequest().body("Authorization code is missing.");
        }

        try {
            KakaoUserDTO kakaoUser = customerService.getKakaoUserInfoFromKakao(code);

            if (customerService.findCustomerById(kakaoUser.getId()).isPresent()) {
                String accessToken = jwtUtil.generateAccessToken(kakaoUser.getId());
                String refreshToken = jwtUtil.generateRefreshToken(kakaoUser.getId());
                return ResponseEntity.ok(Map.of(
                        "accessToken", accessToken,
                        "refreshToken", refreshToken
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                        "status", "NEW_USER",
                        "kakaoUser", kakaoUser
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during Kakao login: " + e.getMessage());
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterDTO dto) {
        Customer customer = customerService.registerCustomer(dto);

        String accessToken = jwtUtil.generateAccessToken(customer.getCustomerId());
        String refreshToken = jwtUtil.generateRefreshToken(customer.getCustomerId());

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        String customerId = jwtUtil.validateAndExtract(refreshToken);

        if (customerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }

        String newAccessToken = jwtUtil.generateAccessToken(customerId);
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}
