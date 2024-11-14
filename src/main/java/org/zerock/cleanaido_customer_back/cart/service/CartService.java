package org.zerock.cleanaido_customer_back.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.cart.dto.CartDetailDTO;
import org.zerock.cleanaido_customer_back.cart.entity.Cart;
import org.zerock.cleanaido_customer_back.cart.entity.CartDetail;
import org.zerock.cleanaido_customer_back.cart.repository.CartDetailRepository;
import org.zerock.cleanaido_customer_back.product.entity.Product;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class CartService {

    private final CartDetailRepository cartDetailRepository;

    public List<CartDetailDTO> listCartDetail(String customerId){

        log.info("--------------");
        log.info("---service start---");

        return cartDetailRepository.list(customerId);
    }

    public Long addCartDetail(Long pno){

        log.info("--------------");
        log.info("--service start---");

        Product product = Product.builder()
                .pno(pno)
                .build();

        Cart cart = Cart.builder()
                .cartNo(1L)
                .build();

        CartDetail cartDetail = CartDetail.builder()
                .cart(cart)
                .product(product) // Product 객체 참조
                .quantity(1)
                .build();

        cartDetailRepository.save(cartDetail); // ProductCategory 저장

        return pno;
    }
}
