package spring.guro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductOrderDetailOption {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문의 각 메뉴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ProductOrderDetail productOrderDetail;

    // 에 선택된 옵션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Option option;

    @Builder
    public ProductOrderDetailOption(ProductOrderDetail productOrderDetail, Option option) {
        this.productOrderDetail = productOrderDetail;
        this.option = option;
    }
}
