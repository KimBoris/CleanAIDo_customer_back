//package org.zerock.cleanaido_customer_back.ai.repository.search;
//
//import com.querydsl.jpa.JPQLQuery;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.zerock.cleanaido_customer_back.category.entity.QCategory;
//import org.zerock.cleanaido_customer_back.category.entity.QProductCategory;
//import org.zerock.cleanaido_customer_back.product.entity.QProduct;
//import org.zerock.cleanaido_customer_back.product.entity.QUsageImageFile;
//
//
//public class AISearchImpl extends QuerydslRepositorySupport implements AISearch {
//
//    public AISearchImpl() {
//        super(QProduct.class);
//    }
//
//    @Override
//    public String getCategorys(String[] imgNames) {
//        QProduct product = QProduct.product;
//        QUsageImageFile usageImageFile = QUsageImageFile.usageImageFile;
//        QProductCategory productCategory = QProductCategory.productCategory;
//        QCategory category = QCategory.category;
//
//        // QueryDSL 쿼리 작성
//        JPQLQuery<String> query = from(product)
//                .join(product.usageImageFiles, usageImageFile)
//                .join(productCategory).on(product.eq(productCategory.product))
//                .join(productCategory.category, category)
//                .where(usageImageFile.fileName.in(imgNames))
//                .select(category.cname)
//                .distinct();
//
//        // 결과를 ','로 이어붙인 문자열로 반환
//        return String.join(",", query.fetch());
//    }
//}
//
