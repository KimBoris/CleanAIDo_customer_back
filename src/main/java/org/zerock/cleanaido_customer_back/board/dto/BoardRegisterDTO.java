package org.zerock.cleanaido_customer_back.board.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardRegisterDTO {
    private Long bno;

    private String title;

    private String description;

    private int viewCount;

    private boolean delFlag;

    private String customerId;


}
