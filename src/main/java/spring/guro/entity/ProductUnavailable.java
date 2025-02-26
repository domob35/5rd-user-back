package spring.guro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductUnavailable {
    // 상품 품절 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // 품절된 지점
    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;

    // 품절 상품
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
