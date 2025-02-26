package spring.guro.controller.newapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.guro.dto.newapi.resp.FaqListResp;
import spring.guro.exception.CustomException;
import spring.guro.service.newapi.FaqService;

@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

    /**
     * FAQ 목록을 페이징하여 조회하는 API 엔드포인트
     * 
     * @param pageable 페이지 정보 (기본값: 날짜 기준 내림차순 정렬)
     *                - sort: date (날짜 기준)
     *                - direction: DESC (내림차순)
     * @param category FAQ 카테고리 (선택적 매개변수)
     *                - null일 경우 전체 카테고리 조회
     * @return 200 OK와 함께 FAQ 목록 및 총 페이지 수 반환
     * @throws CustomException FAQ_NOT_FOUND (조회된 FAQ가 없는 경우)
     */
    @GetMapping
    public ResponseEntity<FaqListResp> getFaqs(
            // 최신 글이 먼저 나타나도록 하기 위해 날짜 기준 desc 정렬
            @PageableDefault(sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String category) { 
        FaqListResp faqs = faqService.getFaqs(pageable, category);
        return ResponseEntity.ok(faqs);
    }
}
