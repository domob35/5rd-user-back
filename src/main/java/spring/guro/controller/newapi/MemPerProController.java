package spring.guro.controller.newapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.req.SaveAsPPReq;
import spring.guro.dto.newapi.resp.PersonalProductResp;
import spring.guro.exception.CustomException;
import spring.guro.service.newapi.MemberService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/personal-products")
@PreAuthorize("isAuthenticated()")
public class MemPerProController {
    private final MemberService memberService;

    /**
     * 제품과 옵션을 사용하여 회원의 개인 맞춤 메뉴를 저장하는 API 엔드포인트입니다.
     *
     * @param req 개인 맞춤 메뉴 생성 요청 DTO (제품ID, 옵션ID 리스트, 메뉴 이름 포함)
     * @return 성공 시 200 OK 응답
     * @implNote 이 메서드는 MemberService를 통해 개인 맞춤 메뉴를 생성하고 저장합니다.
     *          요청 본문의 유효성은 SaveAsPPReq DTO의 검증 어노테이션을 통해 검증됩니다.
     */
    @PostMapping
    public ResponseEntity<?> savePersonalProduct(@RequestBody @Valid SaveAsPPReq req) {
        memberService.savePersonalProduct(req.productId(), req.optionIds(), req.name());
        return ResponseEntity.ok().build();
    }

    /**
     * 현재 로그인한 회원의 개인 맞춤 메뉴 목록을 조회하는 API 엔드포인트입니다.
     * 
     * @return ResponseEntity<List<PersonalProductResp>> 
     *         - 성공 시 200 OK와 함께 개인 맞춤 메뉴 목록 반환
     *         - 각 메뉴는 PersonalProductResp DTO 형태로 반환 (메뉴 ID, 기본 제품 정보, 옵션 목록, 메뉴 이름, 가격 포함)
     * @throws CustomException 
     *         - ErrorEnum.MEMBER_NOT_FOUND: 로그인한 회원이 없는 경우
     *         - ErrorEnum.PERSONAL_PRODUCT_EMPTY: 저장된 개인 맞춤 메뉴가 없는 경우
     */
    @GetMapping
    public ResponseEntity<List<PersonalProductResp>> listPersonalProduct() {
        return ResponseEntity.ok(memberService.listPersonalProduct());
    }
    
    /**
     * 회원의 개인 맞춤 메뉴를 삭제하는 API 엔드포인트입니다.
     *
     * @param id 삭제할 개인 맞춤 메뉴의 ID
     * @return ResponseEntity<?> 
     *         - 성공 시 200 OK 응답
     * @throws CustomException 
     *         - ErrorEnum.PERSONAL_PRODUCT_NOT_FOUND: 해당 ID의 개인 맞춤 메뉴가 존재하지 않는 경우
     * 
     * @implNote 이 메서드는 MemberService를 통해 개인 맞춤 메뉴와 관련된 모든 데이터를 삭제합니다.
     *          개인 맞춤 메뉴 옵션도 CascadeType.REMOVE 설정에 의해 함께 삭제됩니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonalProduct(@PathVariable long id) {
        memberService.deletePersonalProductFromId(id);
        return ResponseEntity.ok().build();
    }
}
