package org.zerock.cleanaido_customer_back.auth.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal; // 사용자 ID

    public JWTAuthenticationToken(String principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true); // 인증 완료 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return null; // JWT 인증은 별도 자격 증명이 필요하지 않음
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
