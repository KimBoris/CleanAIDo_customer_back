package org.zerock.cleanaido_customer_back.cart.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.cleanaido_customer_back.cart.dto.CartDetailDTO;
import org.zerock.cleanaido_customer_back.cart.entity.CartDetail;
import org.zerock.cleanaido_customer_back.cart.entity.QCart;
import org.zerock.cleanaido_customer_back.cart.entity.QCartDetail;
import org.zerock.cleanaido_customer_back.customer.entity.QCustomer;
import org.zerock.cleanaido_customer_back.product.entity.QImageFile;
import org.zerock.cleanaido_customer_back.product.entity.QProduct;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.selectFrom;

@Log4j2
public class CartDetailSearchImpl extends QuerydslRepositorySupport implements CartDetailSearch {

    public CartDetailSearchImpl() {super(CartDetail.class);}

    //장바구니 상세 정보
    @Override
    public List<CartDetailDTO> list(String customerId) {
        QProduct product = QProduct.product;
        QImageFile imageFile = QImageFile.imageFile;
        QCartDetail cartDetail = QCartDetail.cartDetail;
        QCart cart = QCart.cart;
        QCustomer customer = QCustomer.customer;

        JPQLQuery<CartDetailDTO> query = from(cartDetail)
                .join(cartDetail.cart, cart)
                .join(cart.customer, customer)
                .join(cartDetail.product, product)
                .leftJoin(product.imageFiles, imageFile).on(imageFile.ord.eq(0)) // 이미지 파일 중 ord가 0인 파일만 선택
                .where(customer.customerId.eq(customerId))
                .select(Projections.constructor(
                        CartDetailDTO.class,
                        cartDetail.cdno,
                        cart.cartNo,
                        product.pno,
                        product.pname,
                        product.price,
                        cartDetail.quantity,
                        imageFile.fileName // 이미지 파일 경로 추가
                ));

        return query.fetch();
    }


}
