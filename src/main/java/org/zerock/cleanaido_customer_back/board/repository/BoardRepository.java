package org.zerock.cleanaido_customer_back.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.cleanaido_customer_back.board.dto.BoardListDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardReadDTO;
import org.zerock.cleanaido_customer_back.board.entity.Board;
import org.zerock.cleanaido_customer_back.board.repository.search.BoardSearch;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {

    PageResponseDTO<BoardListDTO> list(PageRequestDTO pageRequestDTO);
//
//    @Query("""
//            select b
//            from Board b
//            where b.bno = :bno and b.delFlag = false
//                        """)
//    BoardReadDTO getBoard(@Param("bno") Long bno);


//    BoardReadDTO getBoard(Long bno) ;
}
