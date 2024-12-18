package org.zerock.cleanaido_customer_back.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.zerock.cleanaido_customer_back.customer.entity.Customer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "customer")
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_number", unique = true, nullable = false)
    private Long bno;

    @Column(name = "board_title", length = 100, nullable = false)
    private String title;

    @Column(name = "board_description", length = 2000, nullable = true)
    private String description;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private int viewCount = 0;

    @Builder.Default
    @Column(name="del_flag")
    private boolean delFlag = false;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ElementCollection
    @Builder.Default
    private Set<ImageFile> imageFiles = new HashSet<>();

    public void addImageFile(String filename) {
        imageFiles.add(new ImageFile(imageFiles.size(), filename));
    }
}
