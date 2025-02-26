package spring.guro.service.newapi;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Member;
import spring.guro.repository.newapi.MemberRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserName(userName).orElse(null);
        if (member == null) {
            throw new UsernameNotFoundException(userName);
        }
        return new User(member.getUserName(), member.getPassword(), member.getAuthorities());
    }
}
