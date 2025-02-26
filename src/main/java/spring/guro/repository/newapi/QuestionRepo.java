package spring.guro.repository.newapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.Member;
import spring.guro.entity.Question;

import java.time.LocalDate;

/**
 * 1:1 문의 관련 데이터베이스 조작을 담당하는 리포지토리
 */
public interface QuestionRepo extends JpaRepository<Question, Long> {    
    /**
     * 특정 회원의 문의 내역을 날짜 범위로 필터링하여 페이지네이션된 결과를 최신순으로 조회합니다.
     * 
     * @param member 조회할 회원
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @param pageable 페이지네이션 정보
     * @return 날짜 범위로 필터링된 페이지네이션된 문의 목록
     */
    Page<Question> findAllByMemberAndDateBetweenOrderByDateDesc(
        Member member, 
        LocalDate startDate, 
        LocalDate endDate, 
        Pageable pageable
    );

    Page<Question> findAllByDateBetweenOrderByDateDesc(
        LocalDate startDate, 
        LocalDate endDate, 
        Pageable pageable
    );
}
