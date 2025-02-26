package spring.guro.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product; // 외래 키 (coupon : product = N : 1 )

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member; // 외래 키 (coupon : member = N : 1 )

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private ProductOrder productOrder; // 외래 키 (coupon : productOrder = 1 : 1 )

    @Builder
    public Coupon(Product product, Member member, ProductOrder productOrder) { // 생성자
        this.product = product;
        this.member = member;
        this.productOrder = productOrder;
    }

    public void used(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }

    public static Coupon newCoupon(Member member) {
        Product product = member.getMembership().getProduct();
        Coupon coupon = Coupon.builder()
                .product(product)
                .member(member)
                .build();
        return coupon;
    }
}
