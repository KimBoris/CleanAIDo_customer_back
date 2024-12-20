package org.zerock.cleanaido_customer_back.board.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO {
    private Long bno;

    private String title;

    private String description;

    private LocalDateTime createTime;

    private int viewCount;

    private boolean delFlag;

    private String customerId;

    private String fileName;

}
