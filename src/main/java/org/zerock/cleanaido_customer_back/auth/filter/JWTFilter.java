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
        String path = request.getRequestURI();
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return; // 인증이 필요 없는 경로는 필터 건너뛰기
        }

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            String customerId = jwtUtil.validateAndExtract(token);

            if (customerId != null) {
                SecurityContextHolder.getContext().setAuthentication(new JWTAuthenticationToken(customerId));
            }
        } else {
            // JWT가 없으면 인증 정보를 설정하지 않고 다음 필터로 진행
            System.out.println("Authorization header is missing or invalid for path: " + path);
        }

        filterChain.doFilter(request, response);
    }

}
