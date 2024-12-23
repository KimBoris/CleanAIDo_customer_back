package org.zerock.cleanaido_customer_back.product.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ImageFile {

    @Column(name = "ord")
    private int ord;

    @Column(name="file_name")
    private String fileName;

    //type = false (썸네일)
    //true = 상품이미지 ( 캐럿셀 처리 )
    @Column(name="type")
    private boolean type;

    public boolean getType() {
        return type;
    }
}
