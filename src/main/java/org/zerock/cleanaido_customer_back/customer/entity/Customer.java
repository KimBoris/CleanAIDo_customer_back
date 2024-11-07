package org.zerock.cleanaido_customer_back.customer.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name="customer_pw")
    private String customerPw;

    @Column(name="name")
    private String customerName;

    @Column(name="birth_date")
    private LocalDate birthDate;

    @Column(name="create_date")
    private Timestamp createDate;

    @Column(name="updated_date")
    private Timestamp updatedDate;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="delFlag")
    private boolean delFlag;

    @Column(name="address")
    private String address;

    @Column(name="profile_image_url")
    private String profileImageUrl;

}
