package org.zerock.cleanaido_customer_back.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.cleanaido_customer_back.board.dto.BoardListDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardReadDTO;
import org.zerock.cleanaido_customer_back.board.dto.BoardRegisterDTO;
import org.zerock.cleanaido_customer_back.board.service.BoardService;
import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
import org.zerock.cleanaido_customer_back.common.dto.SearchDTO;
import org.zerock.cleanaido_customer_back.common.dto.UploadDTO;
import org.zerock.cleanaido_customer_back.product.dto.ProductReadDTO;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<BoardListDTO>> list(@RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                                              @RequestParam(value = "keyword", required = false) String keyword,
                                                              @RequestParam(value = "type", required = false) String type) {

        SearchDTO searchDTO = SearchDTO.builder().keyword(keyword).type(type).build();


        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .searchDTO(searchDTO)
                .build();

        if (searchDTO.getKeyword() == null || searchDTO.getType() == null) {
            return ResponseEntity.ok(boardService.listBoard(pageRequestDTO));
        } else {
            return ResponseEntity.ok(boardService.search(pageRequestDTO));
        }

    }

    //    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("register")
    public ResponseEntity<String> register(@ModelAttribute BoardRegisterDTO boardRegisterDTO,
                                           @RequestParam("imageFiles") MultipartFile[] imageFiles) {

        String customerId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boardRegisterDTO.setCustomerId(customerId); // customerId 설정

        UploadDTO imageUploadDTO = new UploadDTO(imageFiles, null);

        Long bno = boardService.registerBoard(boardRegisterDTO, imageUploadDTO);

        return ResponseEntity.ok(bno + "번 게시물이 등록되었습니다.");
    }

    @PutMapping("delete/{bno}")
    public ResponseEntity<String> delete(@PathVariable Long bno) {

        boardService.deleteBoard(bno);

        return ResponseEntity.ok("삭제되었습니다.");
    }

    @GetMapping("{bno}")
    public ResponseEntity<BoardReadDTO> read(@PathVariable Long bno) {
        BoardReadDTO readDTO = boardService.readBoard(bno);
        return ResponseEntity.ok(readDTO);
    }

    @PutMapping(value = "{bno}")
    public ResponseEntity<String> update(
            @PathVariable Long bno,
            @ModelAttribute BoardRegisterDTO boardRegisterDTO) {
        Long updatedBno = boardService.updateBoard(
                bno, boardRegisterDTO);

        return ResponseEntity.ok(updatedBno + "번이 수정 되었습니다.");
    }

}
