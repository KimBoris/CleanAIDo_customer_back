package org.zerock.cleanaido_customer_back.cart.repository.search;

import org.zerock.cleanaido_customer_back.cart.dto.CartDetailDTO;

import java.util.List;

public interface CartDetailSearch {

    List<CartDetailDTO> list(String customerId);
}
