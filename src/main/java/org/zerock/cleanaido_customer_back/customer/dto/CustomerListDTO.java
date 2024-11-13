package org.zerock.cleanaido_customer_back.customer.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

public class CustomerListDTO {
    private String customerId;

    private String customerPw;

    private String customerName;

    private LocalDate birthDate;

    private Timestamp createDate;

    private Timestamp updatedDate;

    private String phoneNumber;

    private boolean delFlag;

    private String address;

    private String profileImageUrl;
}
