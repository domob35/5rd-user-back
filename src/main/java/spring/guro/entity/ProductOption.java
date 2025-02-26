package spring.guro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//새 엔티티 추가
@Getter
@Entity
@NoArgsConstructor
public class ProductOption {
    // id 추가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // product 외래키 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product;

    //option 외래키 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Option option;
}
