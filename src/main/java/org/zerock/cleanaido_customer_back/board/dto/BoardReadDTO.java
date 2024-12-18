package org.zerock.cleanaido_customer_back.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.board.entity.Board;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardReadDTO {
    private Long bno;

    private String title;

    private String description;

    private LocalDateTime createTime;

    private int viewCount;

    private boolean delFlag;

    private String customerId;

    private List<String> imageFiles;
}
