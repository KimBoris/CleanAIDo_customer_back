package org.zerock.cleanaido_customer_back.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserDTO {
    private String id;                // 카카오 ID
    private String nickname;          // 닉네임
    private String email;             // 이메일
    private String profileImageUrl;   // 프로필 이미지 URL
}
