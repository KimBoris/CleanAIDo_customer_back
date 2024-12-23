package org.zerock.cleanaido_customer_back.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.cleanaido_customer_back.board.dto.BoardListDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardReadDTO;
import org.zerock.cleanaido_customer_back.board.entity.Board;
import org.zerock.cleanaido_customer_back.board.entity.ImageFile;
import org.zerock.cleanaido_customer_back.board.entity.QBoard;
import org.zerock.cleanaido_customer_back.board.entity.QImageFile;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.customer.entity.QCustomer;

import java.util.List;


public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    private static final Logger log = LoggerFactory.getLogger(BoardSearchImpl.class);

    public BoardSearchImpl() {
        super(Board.class);
    }

    //게시판 상세
    @Override
    public BoardReadDTO getBoard(Long bno) {
        QBoard board = QBoard.board;
        QCustomer customer = QCustomer.customer;

        JPQLQuery<Board> query = from(board)
                .leftJoin(board.customer, customer)
                .where(board.bno.eq(bno).and(board.delFlag.isFalse()));
        Board result = query.fetchOne();

        List<String> imageFiles = result.getImageFiles().stream().map(ImageFile::getFileName).toList();
        log.info(imageFiles.toString());


        if (result == null) {
            throw new IllegalArgumentException("Board not fount bno : " + bno);
        }
        log.info("==================");
        log.info(result.toString());

        return BoardReadDTO.builder().
                bno(result.getBno())
                .title(result.getTitle())
                .description(result.getDescription())
                .createTime(result.getCreateTime())
                .viewCount(result.getViewCount())
                .customerId(result.getCustomer().getCustomerId())
                .delFlag(result.isDelFlag())
                .imageFiles(imageFiles)
                .build();

    }

    //게시판 리스트
    @Override
    public PageResponseDTO<BoardListDTO> list(PageRequestDTO pageRequestDTO) {

        QBoard board = QBoard.board;
        QCustomer customer = QCustomer.customer;
        QImageFile imageFile = QImageFile.imageFile;
        JPQLQuery<Board> query = from(board).where(board.delFlag.isFalse()).leftJoin(board.customer, customer);
        query.leftJoin(board.imageFiles, imageFile).on(imageFile.ord.eq(0));
        query.orderBy(board.bno.desc());

        Pageable pageable = PageRequest.of
                (pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<BoardListDTO> results = query.select(
                Projections.bean(
                        BoardListDTO.class,
                        board.bno,
                        board.title,
                        board.description,
                        board.createTime,
                        board.viewCount,
                        board.delFlag,
                        customer.customerId.as("customerId"), // DTO 필드와 매핑
                        imageFile.fileName
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

    //게시판 검색
    @Override
    public PageResponseDTO<BoardListDTO> searchBy(String type, String keyword, PageRequestDTO pageRequestDTO) {
        QBoard board = QBoard.board;
        QCustomer customer = QCustomer.customer;

        JPQLQuery<Board> query = from(board)
                .leftJoin(board.customer, customer);

        if (type == null || type.isEmpty()) {
            BooleanBuilder builder = new BooleanBuilder();
            builder.or(board.title.like("%" + keyword + "%"))
                    .or(board.description.like("%" + keyword + "%"));
            query.where(builder).distinct();
        } else if (type.equals("title")) {
            query.where(board.title.like("%" + keyword + "%"));
        } else if (type.equals("description")) {
            query.where(board.description.like("%" + keyword + "%"));
        } else if (type.equals("customer")) {
            query.where(board.customer.customerId.like("%" + keyword + "%"));
        }

        query.orderBy(board.bno.desc());

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());
        getQuerydsl().applyPagination(pageable, query);

        //JPQL 쿼리 결과 대입
        JPQLQuery<BoardListDTO> results = query.select(
                Projections.bean(
                        BoardListDTO.class,
                        board.bno,
                        board.title,
                        board.description,
                        board.createTime,
                        board.viewCount,
                        board.delFlag,
                        customer.customerId.as("customerId")
                )
        );

        List<BoardListDTO> dtoList = results.fetch();
        long total = query.fetchCount();

        return PageResponseDTO.<BoardListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

}
