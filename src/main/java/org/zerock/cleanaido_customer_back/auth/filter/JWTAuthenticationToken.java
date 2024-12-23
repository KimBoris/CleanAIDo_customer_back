package org.zerock.cleanaido_customer_back.auth.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;

//JWT 기반 인증에 필요한 사용자 정보를 담는 커스텀 인증 객체
public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal; // 사용자 ID

    //JWTAuthenticationToken 생성자
    public JWTAuthenticationToken(String principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true); // 인증 완료 상태로 설정
    }

    //자격 증명 반환
    @Override
    public Object getCredentials() {
        return null; // JWT 인증은 별도 자격 증명이 필요하지 않음
    }

    //사용자 ID반환
    @Override
    public Object getPrincipal() {
        return principal;
    }
}
