package org.zerock.cleanaido_customer_back.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.zerock.cleanaido_customer_back.cart.dto.CartDetailDTO;
import org.zerock.cleanaido_customer_back.cart.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    // 장바구니 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<CartDetailDTO>> getCartList() {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Fetching cart list for customerId: {}", customerId);
        return ResponseEntity.ok(cartService.listCartDetail(customerId));
    }

    // 장바구니 항목 추가
    @PostMapping("/add")
    public ResponseEntity<Long> addCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Adding product {} to cart for customerId: {} with quantity: {}", productId, customerId, quantity);

        Long cdno = cartService.addCartDetail(productId, quantity);
        return ResponseEntity.ok(cdno);
    }

    // 장바구니 항목 삭제
    @DeleteMapping("/{cdno}")
    public ResponseEntity<Long> deleteCartItem(@PathVariable Long cdno) {
        log.info("Deleting cart item with cdno: {}", cdno);
        return ResponseEntity.ok(cartService.deleteCartDetail(cdno));
    }

    // 장바구니 수량 업데이트
    @PatchMapping("/{cdno}/quantity")
    public ResponseEntity<Long> updateCartQuantity(@PathVariable Long cdno, @RequestParam int quantity) {
        log.info("Updating cart item quantity: cdno={}, quantity={}", cdno, quantity);
        return ResponseEntity.ok(cartService.updateQty(cdno, quantity));
    }
}
