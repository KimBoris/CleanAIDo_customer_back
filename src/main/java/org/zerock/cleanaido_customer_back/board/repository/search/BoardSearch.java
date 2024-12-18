package org.zerock.cleanaido_customer_back.board.repository.search;

import org.springframework.data.repository.query.Param;
import org.zerock.cleanaido_customer_back.board.dto.BoardListDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardReadDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductListDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;

import java.util.List;

public interface BoardSearch
{

    PageResponseDTO<BoardListDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListDTO> searchBy(String type, String keyword, PageRequestDTO pageRequestDTO);
    BoardReadDTO getBoard(Long bno) ;
}
