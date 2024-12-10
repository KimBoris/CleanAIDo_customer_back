package org.zerock.cleanaido_customer_back.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO {
    private String customerId;
    private String customerPw; // 비밀번호
    private String customerName; // 이름
    private LocalDate birthDate; // 생년월일
    private String phoneNumber; // 전화번호
    private String address; // 주소
    private String profileImageUrl; // 프로필 이미지 URL
}