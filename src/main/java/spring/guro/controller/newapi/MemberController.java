package spring.guro.controller.newapi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import spring.guro.constant.ValidationConstants;
import spring.guro.dto.newapi.req.LoginReq;
import spring.guro.dto.newapi.req.MemberUpdateReq;
import spring.guro.dto.newapi.req.SignUpReq;
import spring.guro.dto.newapi.resp.LoginSuccessResp;
import spring.guro.dto.newapi.resp.MemberAboutCouponResp;
import spring.guro.dto.newapi.resp.MemberAboutMembershipResp;
import spring.guro.dto.newapi.resp.MemberResp;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.dto.newapi.resp.PointResp;
import spring.guro.service.newapi.MemberService;
import spring.guro.service.newapi.NaverLogin;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@PreAuthorize("isAuthenticated()")
public class MemberController {
    private final MemberService memberService;
    private final NaverLogin naverLogin;

    /**
     * 사용자가 로그인할 때 호출되는 엔드포인트입니다.
     * 
     * @param loginReq 사용자의 로그인 요청 정보를 포함하는 객체입니다.
     * @return 로그인 성공 시 JWT 토큰을 포함한 응답을 반환합니다.
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public ResponseEntity<LoginSuccessResp> login(@Valid @ModelAttribute LoginReq loginReq) {
        return ResponseEntity.ok(memberService.login(loginReq));
    }

    /**
     * 새로운 회원을 등록합니다.
     * 
     * @param signUpReq 회원가입에 필요한 정보(이메일, 비밀번호, 사용자 이름, 실명, 전화번호)를 담은 DTO
     * @return 회원가입 성공 시 200(OK) 상태코드 반환
     * @throws CustomException SIGN_UP_EMAIL_DUPLICATION(409) - 이메일이 이미 존재하는 경우
     * @throws MethodArgumentNotValidException VALIDATION_ERROR(400) - 유효성 검증 실패 시
     */
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReq signUpReq) {
        memberService.signUp(signUpReq);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 정보 수정 전에 회원의 비밀번호를 검증합니다.
     * 
     * @param password 현재 회원의 자격 증명과 대조할 비밀번호
     * @return 검증이 성공하면 HTTP 200 OK 상태의 ResponseEntity를 반환
     * @throws CustomException 비밀번호 검증이 실패할 경우
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyBeforeUpdate(@RequestParam("password") String password) {
        memberService.verifyMember(password);
        return ResponseEntity.ok().build();   
    }

    /**
     * 회원 정보를 업데이트하는 엔드포인트입니다.
     * 
     * @param memberUpdateReq 업데이트할 회원 정보를 담은 요청 객체
     *                       (비밀번호, 이메일, 전화번호 포함 가능)
     * @return ResponseEntity<?> 업데이트 성공 시 200 OK 응답
     * @throws CustomException 다음의 경우에 발생:
     *         - 비밀번호가 빈 문자열인 경우
     *         - 이메일이 빈 문자열인 경우
     *         - 전화번호 형식이 올바르지 않은 경우 (형식: 010-XXXX-XXXX 또는 010-XXXX-XXXX)
     */
    @PatchMapping
    public ResponseEntity<?> updateMember(@RequestBody MemberUpdateReq memberUpdateReq) {
        if(memberUpdateReq.password() != null) {
            // 비밀번호가 비어있는지 확인
            if (memberUpdateReq.password().isEmpty()) {
                throw new CustomException(ErrorEnum.VALIDATION_ERROR, "비밀번호는 비워둘 수 없습니다.");
            }
        }
        if(memberUpdateReq.email() != null) {
            // email 패턴이 맞는지 확인
            if (!memberUpdateReq.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                throw new CustomException(ErrorEnum.VALIDATION_ERROR, "이메일 형식이 올바르지 않습니다.");
            }
            // 이메일이 이미 존재하는지 확인
            if (memberService.isOtherUsingEmail(memberUpdateReq.email())) {
                throw new CustomException(ErrorEnum.UPDATE_EMAIL_DUPLICATION);
            }
        }
        if (memberUpdateReq.phone() != null) {
            // 전화번호의 형식이 맞는지 확인
            if (!memberUpdateReq.phone().matches(ValidationConstants.PHONE_NUMBER_PATTERN)) {
                throw new CustomException(ErrorEnum.VALIDATION_ERROR, ValidationConstants.PHONE_NUMBER_MESSAGE);
            }
        }
        memberService.updateMember(memberUpdateReq);
        return ResponseEntity.ok().build();
    }

    /**
     * 현재 로그인한 회원의 멤버십 정보를 조회합니다.
     * 인증된 사용자만 접근 가능합니다.
     *
     * @return ResponseEntity<MemberAboutMembershipResp> 회원의 멤버십 정보를 포함하는 응답
     * @throws CustomException 현재 로그인한 회원이 없는 경우 발생
     */
    @GetMapping("/about/membership")
    public ResponseEntity<MemberAboutMembershipResp> getMembership() {
        return ResponseEntity.ok(memberService.getAboutMembership());
    }

    /**
     * 로그인한 회원의 쿠폰과 스탬프 정보를 조회하는 엔드포인트입니다.
     * 인증된 사용자만 접근할 수 있습니다.
     *
     * @return ResponseEntity<MemberAboutCouponResp> 회원의 쿠폰과 스탬프 정보를 담은 응답 객체
     */
    @GetMapping("/about/gaeggul")
    public ResponseEntity<MemberAboutCouponResp> getAboutCoupon() {
        return ResponseEntity.ok(memberService.getAboutCoupon());
    }

    /**
     * 현재 로그인한 회원의 상세 정보를 조회합니다.
     * 이 엔드포인트는 인증된 사용자만 접근할 수 있습니다.
     *
     * @return ResponseEntity로 감싸진 회원 상세 정보
     * @throws CustomException 현재 로그인한 회원이 없는 경우 발생
     * @see MemberService#getAbout()
     */
    @GetMapping("/about")
    public ResponseEntity<MemberResp> getAbout() {
        return ResponseEntity.ok(memberService.getAbout());
    }

    /**
     * 현재 인증된 회원의 모든 쿠폰 목록을 조회합니다.
     * 
     * @return ResponseEntity로 래핑된 회원의 쿠폰 목록
     * @PreAuthorize("isAuthenticated()") 인증된 사용자만 접근 가능
     */
    @GetMapping("/coupons")
    public ResponseEntity<?> getCoupons() {
        return ResponseEntity.ok(memberService.getCoupons());
    }

    /**
     * 현재 로그인한 사용자가 가진 포인트 조회
     */
    @GetMapping("/current-point")
    public ResponseEntity<PointResp> getCurrentPoint() {
        return ResponseEntity.ok(memberService.getCurrentPoint());
    }

    /*
     * 회원 탈퇴
     */
    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteMember(@PathVariable String userName, @RequestParam("password") String password) {
        memberService.deleteMember(password, userName);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/naver-login")
    public ResponseEntity<LoginSuccessResp> naverLogin(@RequestParam("code") String code) {
        return ResponseEntity.ok(naverLogin.login(code));
    }
}
