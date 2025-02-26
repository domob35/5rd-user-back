package spring.guro.repository.newapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.Faq;

public interface FaqRepo extends JpaRepository<Faq, Long> {
    // 특정 카테고리의 FAQ 목록을 페이징하여 조회
    Page<Faq> findAllByCategory(String category, Pageable pageable);
}
