package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Coupon;
import spring.guro.entity.Member;

import java.util.List;
import java.util.Optional;

public interface CouponRepo extends JpaRepository<Coupon, Long> {
    // 특정 회원이 보유한 모든 쿠폰 조회
    List<Coupon> findAllByMember(Member member);

    // 쿠폰을 회원이 가지고 있으며 사용되지 않았을 경우 반환
    Optional<Coupon> findByIdAndMemberAndProductOrderIsNull(Long id, Member member);
}
