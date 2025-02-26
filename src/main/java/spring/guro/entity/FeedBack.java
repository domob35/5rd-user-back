package spring.guro.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text; // 답변 내용

    @Column(nullable = false)
    private LocalDate date; // 답변 달린 날짜

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Question question; // 외래 키 (FeedBack : Question = 1 : 1)

    @Builder
    public FeedBack(String text, Question question) { // 생성자
        this.text = text;
        this.date = LocalDate.now();
        this.question = question;
    }
}
