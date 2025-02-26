package spring.guro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@NoArgsConstructor
public class PersonalProductOption {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 나만의 메뉴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PersonalProduct personalProduct;

    // 선택된 옵션
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Option option;

    @Builder
    public PersonalProductOption(PersonalProduct personalProduct, Option option) {
        this.personalProduct = personalProduct;
        this.option = option;
    }
}
