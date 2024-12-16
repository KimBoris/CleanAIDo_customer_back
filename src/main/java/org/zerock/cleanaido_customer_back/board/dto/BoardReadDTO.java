package org.zerock.cleanaido_customer_back.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.board.entity.Board;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardReadDTO {

    public BoardReadDTO(Board board) {
        this.bno = board.getBno();
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.createTime = board.getCreateTime();
        this.viewCount = board.getViewCount();
        this.delFlag = board.isDelFlag();

        if (board.getCustomer() != null) {
            this.customerId = board.getCustomer().getCustomerId(); // Customer 객체에서 customerId 추출
        }
    }

    private Long bno;

    private String title;

    private String description;

    private LocalDateTime createTime;

    private int viewCount;

    private boolean delFlag;

    private String customerId;
}
