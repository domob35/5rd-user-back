package spring.guro.repository.newapi;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import spring.guro.entity.Membership;

public interface MembershipRepo extends JpaRepository<Membership, Long> {
    // 기본 멤버십 가져오기 (기본키가 가장 낮은거)
    Membership findFirstByOrderByIdAsc();

    // 요구 금액이 금액 보다 작거나 같은 멤버십 가져오기
    Optional<Membership> findFirstByRequiredPaymentAmountLessThanEqualOrderByIdDesc(long amount);
}
