package spring.guro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 ID

    @Column(nullable = false)
    private String name; // 상품 이름

    @Column(nullable = false)
    private int price; // 상품 가격

    @Column(nullable = false)
    private String image; // 상품 이미지 경로

    @Column(nullable = false)
    private String category; // 상품 카테고리

    @Column(nullable = false)
    private String desc; // 상품 설명

    @Column(nullable = false)
    private String barcode; // 상품 별 바코드 정보

    @Column(nullable = false)
    private int calories; // 상품 열량

    @Column(nullable = false)
    private int carbohydrates; // 상품 탄수화물 함유량

    @Column(nullable = false)
    private int protein; // 상품 단백질 함유량

    @Column(nullable = false)
    private int fat; // 상품 지방 함유량

    @Column(nullable = false)
    private int caffeine; // 상품 카페인 함유량
}
