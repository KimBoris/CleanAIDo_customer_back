//package org.zerock.cleanaido_customer_back.product.dto;
//
//
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.JPQLQuery;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.zerock.cleanaido_customer_back.category.entity.QCategory;
//import org.zerock.cleanaido_customer_back.category.entity.QProductCategory;
//import org.zerock.cleanaido_customer_back.common.dto.PageRequestDTO;
//import org.zerock.cleanaido_customer_back.common.dto.PageResponseDTO;
//import org.zerock.cleanaido_customer_back.product.entity.Product;
//import org.zerock.cleanaido_customer_back.product.entity.QImageFile;
//import org.zerock.cleanaido_customer_back.product.entity.QProduct;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//public class ProductReadDTO {
//
//    private Long pno;
//    private String pname;
//    private int price;
//    private String pstatus;
//    private List<String> thumFileNames;
//    private List<String> detailFileNames;
//    private Double averageScore;
//    private Long countScore;
//
//
//    public ProductReadDTO(Product product, long reviewCnt, double rscore) {
//        this.pno = product.getPno();
//        this.pname = product.getPname();
//        this.price = product.getPrice();
//        this.pstatus = product.getPstatus();
//
//        List<String> thumFileNames =
//                product.getImageFiles().stream().filter(imageFile -> imageFile.getType() == false).map(imageFile -> imageFile.getFileName()).toList();
//
//
//        List<String> detailFileNames =
//                product.getImageFiles().stream().filter(imageFile -> imageFile.getType() == true).map(imageFile -> imageFile.getFileName()).toList();
//
//
//        this.thumFileNames = thumFileNames;
//        this.detailFileNames = detailFileNames;
//
//        this.countScore = reviewCnt;
//        this.averageScore = rscore;
//    }
//}
