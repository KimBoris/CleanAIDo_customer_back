package org.zerock.cleanaido_customer_back.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ImageFile {
    @Column(name = "ord")
    private int ord;

    @Column(name="file_name")
    private String fileName;
}
