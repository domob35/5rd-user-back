package spring.guro.repository.newapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 최근 5개의 공지사항 메서드
    List<Notice> findTop5ByOrderByDateDesc();

    // 공지사항 제목으로 검색하는 메서드
    Page<Notice> findAllByTitleContaining(String title, Pageable pageable);
}
