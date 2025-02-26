package spring.guro.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// import authentication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.guro.entity.Member;
import spring.guro.repository.newapi.MemberRepository;
import spring.guro.util.JwtUtil;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilterChain extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);
        
        if (token != null) {
            Member member = validateToken(token);
            if (member != null) {
                setAuthentication(member);
            } else {
                log.info("No member found in validateToken");
            }
        } else {
            log.info("No token found in validateToken");
        }


        filterChain.doFilter(request, response);
    }

    // method for get token from header
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        log.info("No token found in resolveToken");
        return null;
    }

    private Member validateToken(String token) {
        // check token is valid
        // and member is valid
        log.info("validateToken: {}", token);
        String userName = jwtUtil.getUserName(token);
        log.info("validateToken userName: {}", userName);
        return memberRepository.findByUserName(userName).orElse(null);
    }

    // method for set authentication
    private void setAuthentication(Member member) {
        Authentication auth = new UsernamePasswordAuthenticationToken(member.getId(), null,
                member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info("Authentication set for user: {}", member.getUserName());
    }

}
