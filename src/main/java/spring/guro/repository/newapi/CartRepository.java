package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.guro.entity.Cart;
import spring.guro.entity.Member;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMember(Member member);
    // 특정 회원의 장바구니 전체 삭제
    void deleteAllByMember(Member member);
    List<Cart> findAllByMemberAndSelectedTrue(Member member);
}
