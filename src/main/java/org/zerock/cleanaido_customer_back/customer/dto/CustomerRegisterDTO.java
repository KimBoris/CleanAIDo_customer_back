package org.zerock.cleanaido_customer_back.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO {
    private String customerId;        // 카카오 ID
    private String name;              // 이름
    private String phoneNumber;       // 전화번호
    private String address;           // 주소
    private String profileImageUrl;   // 프로필 이미지 URL
}