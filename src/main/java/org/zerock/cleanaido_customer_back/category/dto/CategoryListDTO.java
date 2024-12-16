package org.zerock.cleanaido_customer_back.category.dto;

import lombok.Data;

@Data
public class CategoryListDTO {
    private Long cno;
    private String cname;
    private int depth;
    private Long parent;
}