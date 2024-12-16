package org.zerock.cleanaido_customer_back.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "orderDetails")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number", unique = true, nullable = false)
    private Long orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false) // String 타입인 customerId를 외래 키로 참조
    private Customer customer;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "delivery_address", nullable = false, length = 255)
    private String deliveryAddress;

    @Column(name = "delivery_message", length = 255)
    private String deliveryMessage;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "tracking_number", length = 255)
    private String trackingNumber;

    @Column(name = "order_status", length = 50)
    private String orderStatus;

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
        orderDetail.setOrder(this);
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice() {
        this.totalPrice = orderDetails.stream()
                .mapToInt(OrderDetail::getTotalPrice) // 각 OrderDetail의 총 가격 계산
                .sum();
    }

}
