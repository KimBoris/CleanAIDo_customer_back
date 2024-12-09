package org.zerock.cleanaido_customer_back.ai.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.cleanaido_customer_back.product.entity.QProduct;
import org.zerock.cleanaido_customer_back.product.entity.QUsageImageFile;

import java.util.stream.Collectors;


public class AISearchImpl extends QuerydslRepositorySupport implements AISearch {

    public AISearchImpl() {
        super(QProduct.class);
    }

    @Override
    public String getCategorys(String[] imgNames) {
        QProduct product = QProduct.product;
        QUsageImageFile usageImageFile = QUsageImageFile.usageImageFile;

        // QueryDSL 쿼리 작성
        JPQLQuery<Tuple> query = from(product)
                .join(product.usageImageFiles, usageImageFile)
                .where(usageImageFile.fileName.in(imgNames))
                .select(product.puseCase, product.pusedItem)
                .distinct();

        // Tuple을 문자열로 변환하여 ','로 이어붙이기
        return query.fetch().stream()
                .map(tuple -> tuple.get(product.puseCase) + " - " + tuple.get(product.pusedItem)) // 원하는 형식으로 변환
                .distinct() // 중복 제거 (필요 시)
                .collect(Collectors.joining(","));
    }

}

