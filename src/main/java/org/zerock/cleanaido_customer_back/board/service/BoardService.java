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
import org.zerock.cleanaido_customer_back.board.entity.ImageFile;
import org.zerock.cleanaido_customer_back.board.repository.BoardRepository;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.common.dto.UploadDTO;
import org.zerock.cleanaido_customer_back.common.util.CustomFileUtil;
import org.zerock.cleanaido_customer_back.common.util.S3Uploader;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;
import org.zerock.cleanaido_customer_back.customer.repository.CustomerRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final CustomFileUtil customFileUtil;
    private final CustomerRepository customerRepository;
    private final S3Uploader s3Uploader;


    //게시판 리스트
    public PageResponseDTO<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO) {
        if (pageRequestDTO.getPage() < 1) {
            throw new IllegalArgumentException("페이지 번호는 1이상 이어야 합니다.");
        }
        PageResponseDTO<BoardListDTO> response = boardRepository.list(pageRequestDTO);

        log.info("---------------------");
        log.info(response);
        return response;
    }

    //게시판 검색
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
                        .fileName(board.getFileName())
                        .build()).collect(Collectors.toList());

        log.info(dtoList);

        return new PageResponseDTO<>(dtoList, pageRequestDTO, resultPage.getTotalPage());

    }


    //게시판 등록
    public Long registerBoard(BoardRegisterDTO boardRegisterDTO, UploadDTO imageUploadDTO) {

        Customer customer = customerRepository.findById(boardRegisterDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        log.info("----------------------------");
        log.info(customer);

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

    //이미지 처리
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

    //게시판 상세
    public BoardReadDTO readBoard(Long bno) {

        BoardReadDTO boardReadDTO = boardRepository.getBoard(bno);

        boardReadDTO.setViewCount(boardReadDTO.getViewCount()+1);

        log.info("boardReadDTO = " + boardReadDTO);

        if (boardReadDTO == null) {
            log.info("no board--------------------------");
            throw new EntityNotFoundException("게시판을 찾을 수 없습니다. bno: " + bno);
        }
        return boardReadDTO;
    }

    //게시판 수정
    @Transactional
    public Long updateBoard(
            Long bno,
            BoardRegisterDTO boardRegisterDTO,
            List<String> oldImageFiles,
            UploadDTO imageUploadDTO) {

        List<String> safeOldImageFiles = (oldImageFiles != null) ? oldImageFiles : new ArrayList<>();

        for (String file : safeOldImageFiles) {
            log.info("Safe Old Image File: {}", file);
        }

        Board board = boardRepository.findById(bno)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Board ID: " + bno));

        //게시판 데이터 초기화
        board.setTitle(boardRegisterDTO.getTitle());
        board.setDescription(boardRegisterDTO.getDescription());
        //기존 첨부된 이미지 파일
        List<String> oldFileNames = board.getImageFiles().stream()
                .map(ImageFile::getFileName)
                .collect(Collectors.toList());

        for (String file : oldFileNames) {
            log.info("saved files: {}", file);
        }

        //삭제해야할 파일
        List<String> filesToDelete = oldFileNames.stream()
                .filter(oldFile -> !safeOldImageFiles.contains(oldFile))
                .collect(Collectors.toList());

        //s3에 기존 파일 삭제
        if (!filesToDelete.isEmpty()) {
            for (String file : filesToDelete) {
                s3Uploader.removeS3File(file);
            }
            board.getImageFiles().removeIf(imageFile -> filesToDelete.contains(imageFile.getFileName()));
        }
        //새로운 이미지 파일 처리
        if(imageUploadDTO != null) {
            processImages(board, imageUploadDTO);
        }

        boardRepository.save(board);

        return board.getBno();
    }

    //게시판 softDelete
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
