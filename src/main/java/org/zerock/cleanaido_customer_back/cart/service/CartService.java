package org.zerock.cleanaido_customer_back.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.cart.dto.CartDetailDTO;
import org.zerock.cleanaido_customer_back.cart.entity.Cart;
import org.zerock.cleanaido_customer_back.cart.entity.CartDetail;
import org.zerock.cleanaido_customer_back.cart.repository.CartDetailRepository;
import org.zerock.cleanaido_customer_back.cart.repository.CartRepository;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;
import org.zerock.cleanaido_customer_back.product.entity.Product;
import org.zerock.cleanaido_customer_back.product.repository.ProductRepository;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class CartService {

    private final CartDetailRepository cartDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public List<CartDetailDTO> listCartDetail(String customerId){

        log.info("---service start---");

        return cartDetailRepository.list(customerId);
    }

    public Long addCartDetail(Long pno, int qty) {
        log.info("Adding cart detail - product: {}, quantity: {}", pno, qty);

        // JWT에서 customerId 가져오기
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();

        // customerId를 기반으로 Customer 조회
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));

        // 고객의 장바구니 조회 (CartRepository 사용)
        Cart cart = cartRepository.findByCustomer(customer)
                .orElseGet(() -> { // 장바구니가 없으면 새로 생성
                    Cart newCart = Cart.builder()
                            .customer(customer)
                            .build();
                    return cartRepository.save(newCart); // 장바구니 저장
                });

        // Product 조회
        Product product = productRepository.findById(pno)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Product ID: " + pno));

        // CartDetail 생성 및 추가
        CartDetail cartDetail = CartDetail.builder()
                .cart(cart)
                .product(product)
                .quantity(qty)
                .build();

        cartDetailRepository.save(cartDetail);
        return cartDetail.getCdno();
    }


    public Long deleteCartDetail(Long id){

        cartDetailRepository.deleteById(id);

        return id;
    }

    public Long updateQty(Long cdno, int qty) {
        // 원하는 cart detail을 찾기 위해 cdno로 조회
        CartDetail cartDetail = cartDetailRepository.findById(cdno)
                .orElseThrow(() -> new IllegalArgumentException("해당 cdno에 대한 내역이 없습니다: " + cdno));

        // 수량 업데이트
        cartDetail.setQuantity(qty);

        // 변경 사항 저장
        cartDetailRepository.save(cartDetail);

        return cdno;
    }
}