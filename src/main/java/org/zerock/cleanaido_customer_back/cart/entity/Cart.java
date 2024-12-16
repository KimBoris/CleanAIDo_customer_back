package org.zerock.cleanaido_customer_back.cart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_no")
    private Long cartNo;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false, unique = true)
    private Customer customer;
}
