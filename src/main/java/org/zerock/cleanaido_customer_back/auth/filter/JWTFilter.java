package org.zerock.cleanaido_customer_back.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.cleanaido_customer_back.auth.util.JWTUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        // 인증이 필요 없는 경로를 필터링에서 제외
        String path = request.getRequestURI();
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return; // 필터를 건너뛰고 다음으로 진행
        }

        // Authorization 헤더에서 JWT 토큰 추출
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            String customerId = jwtUtil.validateAndExtract(token);

            if (customerId != null) {
                // JWT 토큰이 유효하면 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(new JWTAuthenticationToken(customerId));
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
