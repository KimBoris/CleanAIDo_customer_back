package org.zerock.cleanaido_customer_back.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.cart.dto.CartDetailDTO;
import org.zerock.cleanaido_customer_back.cart.repository.CartDetailRepository;

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
}
