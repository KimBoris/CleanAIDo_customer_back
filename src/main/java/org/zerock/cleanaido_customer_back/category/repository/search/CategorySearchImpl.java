package org.zerock.cleanaido_customer_back.category.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.zerock.cleanaido_customer_back.category.dto.CategoryListDTO;
import org.zerock.cleanaido_customer_back.category.entity.Category;
import org.zerock.cleanaido_customer_back.category.entity.QCategory;
import org.zerock.cleanaido_customer_back.category.repository.CategoryRepository;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;

import java.util.List;


@Log4j2
public class CategorySearchImpl extends QuerydslRepositorySupport implements CategorySearch {

    public CategorySearchImpl() {
        super(Category.class);
    }

    //카테고리 리스트
    @Override
    public List<CategoryListDTO> getCategoryList() {
        QCategory category = QCategory.category;

        JPQLQuery<CategoryListDTO> query = from(category)
                .select(Projections.bean(
                        CategoryListDTO.class,
                        category.cno,
                        category.cname,
                        category.depth,
                        category.parent
                ));

        List<CategoryListDTO> categoryList = query.fetch();

        log.info("카테고리 전체 조회 결과: {}", categoryList);

        return categoryList;
    }

}
