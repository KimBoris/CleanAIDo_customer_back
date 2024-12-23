package org.zerock.cleanaido_customer_back.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.cleanaido_customer_back.auth.util.JWTUtil;

import java.io.IOException;


// JWTFilter 클래스는 OncePerRequestFilter를 상속받아 HTTP 요청마다 JWT(Token) 검증을 처리합니다.
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    //특정 경로에 대해 필터링을 수행하지 않도록 설정합니다.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/"); // 인증이 필요하지 않은 경로 제외
    }

    //JWT 토큰을 검사하고, 유효한 경우 SecurityContext에 인증 정보를 설정합니다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            String customerId = jwtUtil.validateAndExtract(token);

            if (customerId != null) {
                // 권한 없이 인증만 설정
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(customerId, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
