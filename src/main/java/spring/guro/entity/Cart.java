package spring.guro.entity;

import java.util.ArrayList;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Column(nullable = false)  
    private int mount; // 제품 수량

    @Column(nullable = false)  
    private double price; // 총 가격 (옵션 포함)

    @Column(nullable = false)  
    private boolean selected; // 주문 선택 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member; // 외래 키 (cart : member = N : 1)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Product product; // 외래 키 (cart : product = N : 1)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Branch branch; // 외래 키 (cart : branch = N : 1)

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CartProductOption> cartProductOptions = new ArrayList<>();

    @Builder
    public Cart(int mount, double price, Member member, Product product, Branch branch) { // 생성자
        this.mount = mount;
        this.price = price;
        this.selected = true;
        this.member = member;
        this.product = product;
        this.branch = branch;
    }

    public void toggleSelected(boolean selected) {
        this.selected = selected;
    }
    
    public void updateMount(int mount) {
        this.mount = mount;
    }

    public void updatePrice(double price) {
        this.price = price;
    }
}
