package org.zerock.cleanaido_customer_back.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.auth.service.KakaoService;
import org.zerock.cleanaido_customer_back.customer.dto.CustomerRegisterDTO;
import org.zerock.cleanaido_customer_back.customer.dto.KakaoUserDTO;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;

import java.sql.Timestamp;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final KakaoService kakaoService;

    // FCM 토큰 저장
    public void updateFcmToken(String customerId, String fcmToken) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
        customer.setFcmToken(fcmToken);
    }

    // 사용자 조회
    public Optional<Customer> findCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    //고객 등록
    public Customer registerCustomer(CustomerRegisterDTO dto) {
        if (dto.getCustomerId() == null || dto.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("Customer ID (email) is required for registration.");
        }

        Customer customer = Customer.builder()
                .customerId(dto.getCustomerId())
                .customerPw(dto.getCustomerPw())
                .customerName(dto.getCustomerName())
                .birthDate(dto.getBirthDate())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .profileImageUrl(dto.getProfileImageUrl())
                .createDate(new Timestamp(System.currentTimeMillis()))
                .delFlag(false)
                .build();

        return customerRepository.save(customer);
    }

    //고객 정보 가져오기
    public Customer getCustomerInfo() {
        // SecurityContextHolder에서 JWT에 담긴 customerId 가져오기
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();

        // customerId를 기반으로 데이터베이스에서 조회
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
    }


    // 카카오 사용자 정보로 조회
    public KakaoUserDTO getKakaoUserInfoFromKakao(String code) {
        String accessToken = kakaoService.getAccessToken(code);
        return kakaoService.getUserInfo(accessToken);
    }
}
