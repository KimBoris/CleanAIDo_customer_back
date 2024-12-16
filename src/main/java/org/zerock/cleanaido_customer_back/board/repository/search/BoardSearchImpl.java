package org.zerock.cleanaido_customer_back.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.cleanaido_customer_back.board.dto.BoardListDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardReadDTO;
import org.zerock.cleanaido_customer_back.board.entity.Board;
import org.zerock.cleanaido_customer_back.board.entity.QBoard;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.customer.entity.QCustomer;

import java.util.List;


public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

//    @Override
//    public BoardReadDTO getBoard(Long bno) {
//        QBoard board = QBoard.board;
//        QCustomer customer = QCustomer.customer;
//
//        JPQLQuery<Board> query = from(board).where(board.bno.eq(bno));
//        Board result = query.fetchOne();
//
////        JPQLQuery<String> customerId = result.getCustomer().getCustomerId();
//
//        if (result == null) {
//            throw new IllegalArgumentException("Board not fount bno : " + bno);
//        }
//
//        return BoardReadDTO.builder().
//                bno(result.getBno())
//                .title(result.getTitle())
//                .description(result.getDescription())
//                .createTime(result.getCreateTime())
//                .viewCount(result.getViewCount())
//                .customerId(String.valueOf(customer.customerId))
//                .delFlag(result.isDelFlag())
//                .build();
//
//    }

    @Override
    public PageResponseDTO<BoardListDTO> list(PageRequestDTO pageRequestDTO) {

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        query.orderBy(board.bno.desc());

        Pageable pageable = PageRequest.of
                (pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<BoardListDTO> results =
                query.select(
                        Projections.bean(
                                BoardListDTO.class,
                                board.bno,
                                board.title,
                                board.description,
                                board.createTime,
                                board.viewCount,
                                board.delFlag
                        )
                );

        List<BoardListDTO> dtoList = results.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<BoardListDTO>withAll().
                dtoList(dtoList).
                totalCount(total).
                pageRequestDTO(pageRequestDTO).
                build();


    }

    @Override
    public PageResponseDTO<BoardListDTO> searchBy(String type, String keyword, PageRequestDTO pageRequestDTO) {
        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);
        query.orderBy(board.bno.desc());

        if (type == null || type.isEmpty()) {
            BooleanBuilder builder = new BooleanBuilder();
            builder.or(board.title.like("%" + keyword + "%"))
                    .or(board.description.like("%" + keyword + "%"));
            query.where(builder).distinct();
        } else if (type.equals("title")) {
            query.where(board.title.like("%" + keyword + "%"));
        } else if (type.equals("description")) {
            query.where(board.description.like("%" + keyword + "%"));
        }

        //사용자 추가하면 주석 풀어야함
//        else if( type.equals("writer"))
//        {
//            BooleanBuilder builder = new BooleanBuilder();
//            builder.or(board.writer.like("%"+keyword+"%"));
//            query.where(builder).distinct();
//        }


        query.orderBy(board.bno.desc());

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<BoardListDTO> results =
                query.select(
                        Projections.bean(
                                BoardListDTO.class,
                                board.bno,
                                board.title,
                                board.description,
                                board.createTime,
                                board.viewCount,
                                board.delFlag
                        ));

        List<BoardListDTO> dtoList = results.fetch();

        long total = query.fetchCount();


        return PageResponseDTO.<BoardListDTO>withAll().
                dtoList(dtoList).
                totalCount(total).
                pageRequestDTO(pageRequestDTO).
                build();
    }
}
