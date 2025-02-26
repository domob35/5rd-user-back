package spring.guro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@NoArgsConstructor
public class CartProductOption {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카트의 메뉴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Cart cart;

    // 메뉴에 선택된 옵션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Option option;

    @Builder
    public CartProductOption(Cart cart, Option option) {
        this.cart = cart;
        this.option = option;
    }
}
