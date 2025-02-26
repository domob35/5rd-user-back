package spring.guro.service.newapi;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import spring.guro.entity.Member;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.MemberRepository;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    public Member getLoginMember() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long id = (Long) authentication.getPrincipal();
            return memberRepository.findById(id).get();
        } catch (Exception e) {
            throw new CustomException(ErrorEnum.UNAUTHORIZED);
        }
    }
}
