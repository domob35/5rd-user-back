package spring.guro.repository.newapi;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.FeedBack;

public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {
    List<FeedBack> findByDateBetween(LocalDate startDate, LocalDate endDate); // 날짜 범위로 질문 조회
}
