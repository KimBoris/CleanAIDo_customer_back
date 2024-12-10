package org.zerock.cleanaido_customer_back.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

            String email = kakaoUser.getEmail();
            if (email == null || email.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email not available in Kakao account. Please ensure email sharing is enabled.");
            }

            if (customerService.findCustomerById(email).isPresent()) {
                String accessToken = jwtUtil.generateAccessToken(email);
                String refreshToken = jwtUtil.generateRefreshToken(email);
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid Kakao code or token: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during Kakao login: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegisterDTO dto) {
        try {
            Customer customer = customerService.registerCustomer(dto);

            String accessToken = jwtUtil.generateAccessToken(customer.getCustomerId());
            String refreshToken = jwtUtil.generateRefreshToken(customer.getCustomerId());

            return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid registration data: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registration: " + e.getMessage());
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            String customerId = jwtUtil.validateAndExtract(refreshToken);

            if (customerId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired Refresh Token");
            }

            String newAccessToken = jwtUtil.generateAccessToken(customerId);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during token refresh: " + e.getMessage());
        }
    }
}
