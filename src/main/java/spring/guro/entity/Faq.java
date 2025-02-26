package spring.guro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // FAQ ID (기본키)

    @Column(nullable = false)
    private String category; // 카테고리

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private LocalDate date; // 작성일

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 내용
}
