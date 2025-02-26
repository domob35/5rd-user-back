package spring.guro.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BranchInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Ingredient ingredient;

    //재고 판매 불가 옵션
    @Column(nullable = false)
    private boolean unavailable;

    public void decreaseStock(int i) {
        this.quantity -= i;
    }

    // //생성자
    // @Builder
    // public BranchInventory(int igQuantity, Branch branch, Ingredient ingredient, boolean unavailable) {
    //     this.igQuantity = igQuantity;
    //     this.branch = branch;
    //     this.ingredient = ingredient;
    //     this.unavailable = unavailable;
    // }

}

