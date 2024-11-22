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

        log.info("---service start---");

        return cartDetailRepository.list(customerId);
    }

    public Long addCartDetail(Long pno, int qty){

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
                .quantity(qty)
                .build();

        cartDetailRepository.save(cartDetail); // ProductCategory 저장

        return pno;
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
