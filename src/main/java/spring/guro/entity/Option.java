package spring.guro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 옵션 ID (기본키)

    @Column(nullable = false)
    private String name; // 옵션 이름

    @Column(nullable = true)
    private String group; // 선택 타입 (nullable, n중1택형 옵션을 위해)

    @Column(nullable = false)
    private boolean multiSelect; // 다중선택 여부

    @Column(nullable = false)
    private int price; // 옵션 가격
}
