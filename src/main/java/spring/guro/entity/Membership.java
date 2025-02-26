package spring.guro.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor

public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 멤버십 ID (기본키)

    @Column(nullable = false)
    private String name; // 멤버십 이름

    @Column(nullable = false)
    private long requiredPaymentAmount; // 멤버십을 위한 총 결제 금액

    @Column(nullable = false)
    private double pointRate; // 적립률 (0.01 = 1%)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product; // 멤버십 상품
}
