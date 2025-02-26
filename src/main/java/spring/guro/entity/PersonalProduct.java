package spring.guro.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PersonalProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false)
    private String name; // 나만의 메뉴 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member; // 외래 키 (personal_product : member = N : 1)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product; // 외래 키 (personal_product : product = N : 1)

    @OneToMany(mappedBy = "personalProduct", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PersonalProductOption> personalProductOptions; // 외래 키 (personal_product : personal_product_option = 1 : N)

    @Builder
    public PersonalProduct(String name, Member member, Product product) { // 생성자
        this.name = name;
        this.member = member;
        this.product = product;
    }
}