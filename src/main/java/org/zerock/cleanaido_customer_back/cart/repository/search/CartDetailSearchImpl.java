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

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.selectFrom;

@Log4j2
public class CartDetailSearchImpl extends QuerydslRepositorySupport implements CartDetailSearch {

    public CartDetailSearchImpl() {super(CartDetail.class);}

    @Override
    public List<CartDetailDTO> list(String customerId) {
        QCartDetail cartDetail = QCartDetail.cartDetail;
        QCart cart = QCart.cart;
        QCustomer customer = QCustomer.customer;

        JPQLQuery<CartDetailDTO> query = from(cartDetail)
                .join(cartDetail.cart, cart)
                .join(cart.customer, customer)
                .where(customer.customerId.eq(customerId))
                .select(Projections.constructor(
                        CartDetailDTO.class,
                        cartDetail.cdno,
                        cart.cartNo,
                        cartDetail.product.pno,
                        cartDetail.product.pname,
                        cartDetail.product.price,
                        cartDetail.quantity
                ));

        return query.fetch();
    }


}
