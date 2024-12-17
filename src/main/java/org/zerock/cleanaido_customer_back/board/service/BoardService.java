package org.zerock.cleanaido_customer_back.board.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.cleanaido_customer_back.board.dto.BoardListDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardReadDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardRegisterDTO;
import org.zerock.cleanaido_customer_back.board.entity.Board;
import org.zerock.cleanaido_customer_back.board.repository.BoardRepository;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.common.dto.UploadDTO;
import org.zerock.cleanaido_customer_back.common.util.CustomFileUtil;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final CustomFileUtil customFileUtil;
    private final CustomerRepository customerRepository;
//    private final S3Uploader s3Uploader;


    public PageResponseDTO<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO) {
        if (pageRequestDTO.getPage() < 1) {
            throw new IllegalArgumentException("페이지 번호는 1이상 이어야 합니다.");
        }
        PageResponseDTO<BoardListDTO> response = boardRepository.list(pageRequestDTO);

        return response;
    }

    public PageResponseDTO<BoardListDTO> search(PageRequestDTO pageRequestDTO) {
        String keyword = pageRequestDTO.getSearchDTO().getKeyword();
        String type = pageRequestDTO.getSearchDTO().getType();

        PageResponseDTO<BoardListDTO> resultPage = boardRepository.searchBy(type, keyword, pageRequestDTO);

        List<BoardListDTO> dtoList = resultPage.getDtoList().stream()
                .map(board -> BoardListDTO.builder()
                        .bno(board.getBno())
                        .title(board.getTitle())
                        .description(board.getDescription())
                        .createTime(board.getCreateTime())
                        .delFlag(board.isDelFlag())
                        .viewCount(board.getViewCount())
                        .customerId(board.getCustomerId())
                        .build()).collect(Collectors.toList());

        log.info(dtoList);

        return new PageResponseDTO<>(dtoList, pageRequestDTO, resultPage.getTotalPage());

    }


    public Long registerBoard(BoardRegisterDTO boardRegisterDTO, UploadDTO imageUploadDTO) {

        Customer customer = customerRepository.findById(boardRegisterDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Board board = Board.builder().
                bno(boardRegisterDTO.getBno()).
                title(boardRegisterDTO.getTitle()).
                description(boardRegisterDTO.getDescription()).
                viewCount(boardRegisterDTO.getViewCount()).
                delFlag(boardRegisterDTO.isDelFlag()).
                customer(customer)
                .build();

        processImages(board, imageUploadDTO);

        boardRepository.save(board);
        log.info("================");
        log.info(board);
        log.info(imageUploadDTO);
        log.info("================");

        return board.getBno();
    }

    private void processImages(Board board, UploadDTO uploadDTO) {
        log.info("Processing image for board: {}", board.getBno());
        log.info("Upload image: {}", uploadDTO);

        List<String> fileNames = Optional.ofNullable(uploadDTO.getFiles())
                .map(files -> Arrays.stream(files)
                        .filter(file -> !file.isEmpty())
                        .collect(Collectors.toList()))
                .filter(validFiles -> !validFiles.isEmpty())
                .map(customFileUtil::saveFiles)
                .orElse(Collections.emptyList());


        for (String filename : fileNames) {
            board.addImageFile(filename);
        }
    }

    public BoardReadDTO readBoard(Long bno) {

        BoardReadDTO boardReadDTO = boardRepository.getBoard(bno);

        log.info("boardReadDTO = " + boardReadDTO);

        if (boardReadDTO == null) {
            log.info("no board--------------------------");
            throw new EntityNotFoundException("게시판을 찾을 수 없습니다. bno: " + bno);
        }
        return boardReadDTO;
    }

    @Transactional
    public Long updateBoard(
            Long bno,
            BoardRegisterDTO boardRegisterDTO) {

        Board board = boardRepository.findById(bno)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Board ID: " + bno));

        board.setTitle(boardRegisterDTO.getTitle());
        board.setDescription(boardRegisterDTO.getDescription());

        boardRepository.save(board);

        return board.getBno();
    }

    public String deleteBoard(Long bno) {
        Board board = boardRepository.findById(bno).orElseThrow(()
                -> new EntityNotFoundException(bno + "를 찾을 수 없습니다."));

        log.info(bno);
        log.info(board);

        board.setDelFlag(true);
        boardRepository.save(board);

        return bno + "가 삭제되었습니다.";
    }
}
