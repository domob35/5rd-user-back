package spring.guro.service.newapi;

import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.req.LoginReq;
import spring.guro.dto.newapi.req.MemberUpdateReq;
import spring.guro.dto.newapi.req.SignUpReq;
import spring.guro.dto.newapi.resp.CouponResp;
import spring.guro.dto.newapi.resp.LoginSuccessResp;
import spring.guro.dto.newapi.resp.MemberAboutCouponResp;
import spring.guro.dto.newapi.resp.MemberAboutMembershipResp;
import spring.guro.dto.newapi.resp.MemberResp;
import spring.guro.dto.newapi.resp.PersonalProductResp;
import spring.guro.dto.newapi.resp.PointResp;
import spring.guro.entity.Member;
import spring.guro.entity.Membership;
import spring.guro.entity.Option;
import spring.guro.entity.PersonalProduct;
import spring.guro.entity.Product;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.MemberRepository;
import spring.guro.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final MembershipService membershipService;
    private final PersonalProductService personalProductService;
    private final ProductService productService;
    private final OptionService optionService;
    private final PersonalProductOptionService personalProductOptionService;

    public boolean isUserNameExist(String userName) {
        return memberRepository.existsByUserName(userName);
    }

    public boolean isEmailExist(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean isOtherUsingEmail(String email) {
        Member member = authService.getLoginMember();
        return memberRepository.existsByEmailAndIdNot(email, member.getId());
    }

    public LoginSuccessResp login(LoginReq loginReq) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginReq.userName(), loginReq.password()));

        Member member = memberRepository.findByUserName(loginReq.userName()).get();

        return new LoginSuccessResp(jwtUtil.createToken(member), member.getUserName(), member.getAuthority(), false);
    }

    @Transactional
    public void signUp(SignUpReq signUpReq) {
        if (isUserNameExist(signUpReq.userName())) {
            throw new CustomException(ErrorEnum.SIGN_UP_USER_NAME_DUPLICATION);
        }

        if (isEmailExist(signUpReq.email())) {
            throw new CustomException(ErrorEnum.SIGN_UP_EMAIL_DUPLICATION);
        }

        memberRepository.findByUserName(signUpReq.userName()).ifPresent(member -> {
            throw new CustomException(ErrorEnum.SIGN_UP_USER_NAME_DUPLICATION);
        });

        Member member = Member.builder()
                .email(signUpReq.email())
                .password(passwordEncoder.encode(signUpReq.password()))
                .userName(signUpReq.userName())
                .realName(signUpReq.realName())
                .phone(signUpReq.phone())
                .membership(membershipService.getDefaultMembership())
                .build();
        memberRepository.save(member);
    }

    /**
     * 회원의 비밀번호를 저장된 인증 정보와 대조하여 검증합니다.
     * 
     * @param password 로그인한 회원의 인증 정보와 대조할 비밀번호
     * @throws CustomException 비밀번호가 일치하지 않을 경우 ErrorEnum.INVALID_PASSWORD를 발생시킵니다
     */
    public void verifyMember(String password) {
        Member member = authService.getLoginMember();
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorEnum.INVALID_PASSWORD);
        }
    }

    /**
     * 제공된 요청에 따라 회원 정보를 업데이트합니다.
     * 요청에서 null이 아닌 필드만 업데이트합니다.
     * 
     * @param memberUpdateReq 업데이트할 회원 정보(비밀번호, 이메일, 전화번호, 이름)을 포함하는 요청
     * @throws CustomException 현재 로그인한 회원이 없는 경우 발생
     * @see MemberUpdateReq
     * @see Member
     */
    @Transactional
    public void updateMember(MemberUpdateReq memberUpdateReq) {
        Member member = authService.getLoginMember();
        if (memberUpdateReq.password() != null) {
            member.updatePassword(passwordEncoder.encode(memberUpdateReq.password()));
        }
        if (memberUpdateReq.email() != null) {
            member.updateEmail(memberUpdateReq.email());
        }
        if (memberUpdateReq.phone() != null) {
            member.updatePhone(memberUpdateReq.phone());
        }
        if (memberUpdateReq.realName() != null) {
            member.updateRealName(memberUpdateReq.realName());
        }
        memberRepository.save(member);
    }

    /**
     * 현재 회원의 멤버십 정보를 조회합니다.
     * 인증 서비스를 통해 로그인된 회원의 데이터를 가져와 멤버십 세부 정보를 반환합니다.
     *
     * @return MemberAboutMembershipResp 다음 정보를 포함:
     *         - 멤버십 이름
     *         - 포인트 적립률
     *         - 현재 포인트 잔액
     *         - 총 결제 금액
     */
    public MemberAboutMembershipResp getAboutMembership() {
        Member member = authService.getLoginMember();
        Membership membership = member.getMembership();
        return MemberAboutMembershipResp.builder()
                .membershipName(membership.getName())
                .pointRate(membership.getPointRate())
                .point(member.getPoint())
                .totalPaymentAmount(member.getTotalPaymentAmount())
                .totalPurchaseCount(member.getTotalPurchaseCount())
                .build();
    }

    /**
     * 현재 로그인한 회원의 쿠폰과 스탬프 데이터를 조회합니다
     *
     * @return 회원의 쿠폰과 스탬프 정보를 포함하는 MemberCouponResp
     * @throws CustomException 현재 로그인한 회원이 없는 경우 발생
     */
    public MemberAboutCouponResp getAboutCoupon() {
        Member member = authService.getLoginMember();
        return MemberAboutCouponResp.builder()
                .coupons(member.getCoupons().stream().filter(c -> c.getProductOrder() == null).map(
                        c -> CouponResp.builder()
                                .productName(c.getProduct().getName())
                                .build())
                        .toList())
                .stamp(member.getStamp())
                .build();
    }

    /**
     * 사용자의 현재 포인트 조회
     */
    public PointResp getCurrentPoint() {
        // 최신 포인트 정보를 가져와 DTO로 반환
        Member member = authService.getLoginMember();
        return new PointResp(member.getPoint());
    }

    /**
     * 현재 로그인한 회원의 미사용 쿠폰 목록을 조회합니다.
     * 
     * @return 사용 가능한 쿠폰 목록을 CouponResp 타입으로 변환하여 반환
     *         이미 사용된 쿠폰(productOrder가 있는 쿠폰)은 제외됩니다.
     * @throws CustomException 현재 로그인한 회원이 없는 경우 발생
     */
    public List<CouponResp> getCoupons() {
        Member member = authService.getLoginMember();
        return member.getCoupons().stream().map(coupon -> {
            if (coupon.getProductOrder() == null) {
                return new CouponResp(coupon);
            } else {
                return null;
            }
        }).filter(Objects::nonNull).toList();
    }

    public MemberResp getAbout() {
        Member member = authService.getLoginMember();
        return new MemberResp(member);
    }

    /**
     * 회원 탈퇴를 처리하는 메서드입니다.
     * 
     * @param password 회원의 계정 비밀번호
     * @param userName 탈퇴할 회원의 사용자명
     * @throws CustomException 다음의 경우에 발생:
     *         - ErrorEnum.MEMBER_NOT_FOUND: 해당 사용자명의 회원이 존재하지 않는 경우
     *         - ErrorEnum.INVALID_PASSWORD: 입력한 비밀번호가 일치하지 않는 경우
     */
    @Transactional
    public void deleteMember(String password, String userName) {
        Member member = memberRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(ErrorEnum.MEMBER_NOT_FOUND));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ErrorEnum.INVALID_PASSWORD);
        }
        memberRepository.delete(member);
    }

    /**
     * 회원의 개인 맞춤 메뉴를 생성하고 저장합니다.
     * 기존 제품을 기반으로 회원이 선택한 옵션들과 함께 개인 맞춤 메뉴를 만듭니다.
     * 
     * @param productId 개인 맞춤 메뉴의 기본이 되는 제품 ID
     * @param optionIds 개인 맞춤 메뉴에 추가할 옵션들의 ID 목록
     * @param ppName 개인 맞춤 메뉴의 이름
     * 
     * @throws CustomException 다음의 경우에 발생할 수 있습니다:
     *         - ErrorEnum.MEMBER_NOT_FOUND: 로그인한 회원 정보를 찾을 수 없는 경우
     *         - ErrorEnum.PRODUCT_EMPTY: 지정된 제품 ID가 존재하지 않는 경우
     *         - ErrorEnum.OPTION_NOT_FOUND: 지정된 옵션 ID가 존재하지 않는 경우
     * 
     * @implNote 이 메서드는 트랜잭션으로 관리되며, 모든 작업이 성공적으로 완료되거나
     *          오류 발생 시 모든 변경사항이 롤백됩니다.
     */
    @Transactional
    public void savePersonalProduct(long productId, List<Long> optionIds, String ppName) {
        Member member = authService.getLoginMember();
        Product product = productService.getFromId(productId);
        PersonalProduct personalProduct = personalProductService.saveFromProduct(product, member, ppName);
        optionIds.stream().forEach(id -> {
            Option option = optionService.getFromId(id);
            personalProductOptionService.saveFromOption(option, personalProduct);
        });
    }

    public List<PersonalProductResp> listPersonalProduct() {
        Member member = authService.getLoginMember();
        return personalProductService.listPersonalProductFromMember(member);
    }

    @Transactional
    public void deletePersonalProductFromId(long id) {
        personalProductService.delete(id);
    }

    public Member findMemberByPhone(String phone) {
        return memberRepository.findByPhone(phone).orElseThrow(() -> new CustomException(ErrorEnum.MEMBER_NOT_FOUND));
    }
}
