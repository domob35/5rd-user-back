package spring.guro.service.newapi;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Member;
import spring.guro.entity.Membership;
import spring.guro.repository.newapi.MembershipRepo;

@Service
@RequiredArgsConstructor

public class MembershipService {
    private final MembershipRepo membershipRepo;

    public Membership getDefaultMembership() {
        return membershipRepo.findFirstByOrderByIdAsc();
    }

    public void upgradeMembership(Member member) {
        membershipRepo.findFirstByRequiredPaymentAmountLessThanEqualOrderByIdDesc(member.getTotalPaymentAmount()).ifPresent(
            membership -> {
                if(member.getMembership().getId() != membership.getId()) {
                    member.upgradeMembership(membership);
                }
            }
        );
    }
}