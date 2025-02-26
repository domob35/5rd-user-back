package spring.guro.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Affiliated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    @Column(nullable = false)
    private String category; // 상담 카테고리

    @Column(nullable = false)
    private String name; // 상담자 이름

    @Column(nullable = false)
    private String phone; // 전화번호

    @Column(nullable = false)
    private String location; // 희망 지역

    @Lob 
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용

    @Builder
    public Affiliated(String category, String name, String phone, String location, String content) {
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.content = content;
    }
}
