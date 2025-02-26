package spring.guro.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본키

    @Column(nullable = false)
    private String title; // 질문 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text; // 질문 내용

    @Column(nullable = false)
    private String category; // 질문 카테고리

    @Column(nullable = false)
    private LocalDate date; // 질문날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member; //member 참조

    @OneToOne(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private FeedBack feedBack;

    @Builder
    public Question(String title, String text, String category, Member member) {
        this.title = title;
        this.text = text;
        this.category = category;
        this.date = LocalDate.now();
        this.member = member;
    }
}
