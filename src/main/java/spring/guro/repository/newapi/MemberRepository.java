package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUserName(String userName);
    Optional<Member> findByRealName(String realName);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    // 특정 회원의 현재 포인트 조회
    int findPointById(Long id);
    // 다른 회원의 이메일과 중복되는지 확인
    boolean existsByEmailAndIdNot(String email, Long id); 
    Optional<Member> findByPhone(String phone);
}
