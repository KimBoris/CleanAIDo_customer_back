package org.zerock.cleanaido_customer_back.board.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

}
