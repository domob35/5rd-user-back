package spring.guro.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class ProductOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    // 각 메뉴의 옵션 포함 가격
    @Column(nullable = false)
    private int subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ProductOrder productOrder;

    @OneToMany(mappedBy = "productOrderDetail", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductOrderDetailOption> productOrderDetailOptions;

    @Builder
    public ProductOrderDetail(int quantity, int subtotal, Product product, ProductOrder productOrder) {
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.product = product;
        this.productOrder = productOrder;
    }
}
