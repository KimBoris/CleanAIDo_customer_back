package org.zerock.cleanaido_customer_back.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.cleanaido_customer_back.product.entity.Product;

import javax.annotation.security.DenyAll;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReadDTO {

    private Long pno;
    private String pname;
    private int price;
    private String pstatus;
    private List<String> thumFileNames;
    private List<String> detailFileNames;
    private Double averageScore;
    private Long countScore;
    private String category;


    public ProductReadDTO(Product product, long reviewCnt, double rscore) {
        this.pno = product.getPno();
        this.pname = product.getPname();
        this.price = product.getPrice();
        this.pstatus = product.getPstatus();

        List<String> thumFileNames =
                product.getImageFiles().stream().filter(imageFile -> imageFile.getType() == false).map(imageFile -> imageFile.getFileName()).toList();


        List<String> detailFileNames =
                product.getImageFiles().stream().filter(imageFile -> imageFile.getType() == true).map(imageFile -> imageFile.getFileName()).toList();


        this.thumFileNames = thumFileNames;
        this.detailFileNames = detailFileNames;

        this.countScore = reviewCnt;
        this.averageScore = rscore;

        this.category =product.getCategory().getCname();
    }
}
