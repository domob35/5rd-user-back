package spring.guro.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 공지 ID

    @Column(nullable = false)
    private String title; // 공지 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    @Lob
    private String text; // 공지 내용

    @Column(nullable = false)
    private LocalDate date; // 작성 날짜

    @Column(nullable = false)
    private int view; // 조회수

    @Version
    @Column(nullable = false)
    private long version;  // Optimistic locking을 위한 버전 필드

    @Builder
    public Notice(String title, String text) {
        this.title = title;
        this.text = text;
        this.date = LocalDate.now();
        this.view = 0;
    }

    //조회수 증가 (Optimistic locking 적용)
    public void viewIncrease() {
        view++;
    }
}
