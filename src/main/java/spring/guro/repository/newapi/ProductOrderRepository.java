package spring.guro.repository.newapi;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.Branch;
import spring.guro.entity.Member;
import spring.guro.entity.ProductOrder;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    // 특정 회원의 특정 기간 사이의 모든 주문 조회(페이지네이션 적용)
    Page<ProductOrder> findAllByMemberAndDateBetween(
            Member member, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    // 특정 회원이 특정 지점에서 주문한 총 횟수 조회
    int countByMemberAndBranch(Member member, Branch branch);
}
