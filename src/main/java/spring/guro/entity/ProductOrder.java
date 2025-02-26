package spring.guro.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.guro.enumtype.PaymentMethod;

@Entity
@Getter
@NoArgsConstructor
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false)
    private LocalDateTime date; // 주문일자(날짜+시간)

    @Column(nullable = false)
    private int totalPrice; // 주문 원래 가격

    @Column(nullable = false)
    private int billedAmount; // 청구 금액

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 결제 수단

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    // 외래 키 (ProductOrder : Branch = N : 1)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    // 외래 키 (ProductOrder : Member = N : 1)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private Coupon coupon; // 외래 키 (ProductOrder : Coupon = 1 : 1)

    @OneToMany(mappedBy = "productOrder", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductOrderDetail> productOrderDetails = new ArrayList<>(); // 외래 키 (ProductOrder : ProductOrderDetail = 1 : N)

    @Builder 
    public ProductOrder(int totalPrice, int billedAmount, PaymentMethod paymentMethod, Branch branch, Member member, Coupon coupon) { // 생성자
        this.date = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.billedAmount = billedAmount;
        this.paymentMethod = paymentMethod;
        this.branch = branch;
        this.member = member;
        this.coupon = coupon;
    }
}

