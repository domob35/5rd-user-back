package spring.guro.service.newapi;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.req.SignUpReq;
import spring.guro.dto.newapi.resp.LoginSuccessResp;
import spring.guro.entity.Member;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.property.NaverClientProperties;
import spring.guro.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class NaverLogin {
    private final NaverClientProperties naverClientProperties;
    private final MemberService memberService;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginSuccessResp login(String code) {
        AuthorizationCode authCode = getAuthorizationCode(code);
        NaverUserInfo userInfo = getNaverUserInfo(authCode.accessToken());
        return processUserLogin(userInfo);
    }

    private AuthorizationCode getAuthorizationCode(String code) {
        String uri = buildAuthorizationUri(code);
        ResponseEntity<AuthorizationCode> response = restTemplate.getForEntity(uri, AuthorizationCode.class);
        validateAuthorizationCode(response.getBody());
        return response.getBody();
    }

    private String buildAuthorizationUri(String code) {
        return UriComponentsBuilder.fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", code)
                .queryParam("client_id", naverClientProperties.getId())
                .queryParam("client_secret", naverClientProperties.getSecret())
                .toUriString();
    }

    private void validateAuthorizationCode(AuthorizationCode authCode) {
        if (authCode == null) {
            throw new CustomException(ErrorEnum.NAVER_LOGIN_FAILED, "네이버 로그인에 실패했습니다.");
        }
        if (authCode.error() != null) {
            throw new CustomException(ErrorEnum.NAVER_LOGIN_FAILED, authCode.errorDescription());
        }
    }

    private NaverUserInfo getNaverUserInfo(String accessToken) {
        String uri = buildUserInfoUri();
        HttpHeaders headers = createAuthorizationHeaders(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<NaverUserInfo> response = restTemplate.exchange(
                uri,
                org.springframework.http.HttpMethod.GET,
                entity,
                NaverUserInfo.class
        );
        validateUserInfo(response.getBody());
        return response.getBody();
    }

    private String buildUserInfoUri() {
        return UriComponentsBuilder.fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .toUriString();
    }

    private HttpHeaders createAuthorizationHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private void validateUserInfo(NaverUserInfo userInfo) {
        if (userInfo == null) {
            throw new CustomException(ErrorEnum.NAVER_LOGIN_FAILED, "네이버 사용자 정보를 가져올 수 없습니다.");
        }
    }

    private LoginSuccessResp processUserLogin(NaverUserInfo userInfo) {
        try {
            return loginExistingUser(userInfo);
        } catch (Exception e) {
            return registerAndLoginNewUser(userInfo);
        }
    }

    private LoginSuccessResp loginExistingUser(NaverUserInfo userInfo) {
        Member member = memberService.findMemberByPhone(userInfo.response().mobile());
        return createLoginResponse(member);
    }

    private LoginSuccessResp registerAndLoginNewUser(NaverUserInfo userInfo) {
        registerNewUser(userInfo);
        Member member = memberService.findMemberByPhone(userInfo.response().mobile());
        return createLoginResponse(member);
    }

    private void registerNewUser(NaverUserInfo userInfo) {
        String userName = userInfo.response().email().split("@")[0];
        SignUpReq signUpReq = SignUpReq.builder()
                .email(userInfo.response().email())
                .password(userInfo.response().id())
                .phone(userInfo.response().mobile())
                .realName(userInfo.response().name())
                .userName(userName)
                .build();
        memberService.signUp(signUpReq);
    }

    private LoginSuccessResp createLoginResponse(Member member) {
        String token = jwtUtil.createToken(member);
        return LoginSuccessResp.builder()
                .token(token)
                .userName(member.getUserName())
                .authority(member.getAuthority())
                .socialLogin(true)
                .build();
    }

    private record AuthorizationCode(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("expires_in") Integer expiresIn,
            String error,
            @JsonProperty("error_description") String errorDescription) {
    }

    private record NaverUserInfo(
            String resultcode,
            String message,
            Response response) {
        private record Response(
                String id,
                String email,
                String name,
                String nickname,
                @JsonProperty("profile_image") String profileImage,
                String age,
                String birthday,
                String birthyear,
                String mobile,
                String gender) {
        }
    }
}
