package spring.guro.controller.newapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import spring.guro.dto.newapi.req.CreateFeedbackReq;
import spring.guro.dto.newapi.req.CreateQuestionReq;
import spring.guro.dto.newapi.resp.QuestionListResp;
import spring.guro.dto.newapi.resp.QuestionDetailResp;
import spring.guro.service.newapi.QuestionService;

import java.time.LocalDate;

/**
 * 1:1 문의 관련 API를 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    /**
     * 새로운 1:1 문의를 등록합니다.
     * 
     * @param req 문의 등록 요청 정보
     * @return 성공 시 200 OK
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> createQuestion(@RequestBody CreateQuestionReq req) {
        questionService.createQuestion(req);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자의 1:1 문의 내역을 조회합니다.
     * 
     * @param startDate 조회 시작 날짜 (미입력 시 오늘)
     * @param endDate   조회 종료 날짜 (미입력 시 7일 전)
     * @param pageable  페이지 정보 (기본값: 페이지 크기 10, 페이지 번호 0)
     * @return 문의 내역 목록과 전체 페이지 수
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<QuestionListResp> getQuestions(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(questionService.getQuestionsFromMember(startDate, endDate, pageable));
    }

    /**
     * 특정 1:1 문의의 상세 내용을 조회합니다.
     * 
     * @param id 조회할 문의의 ID
     * @return 문의 상세 정보
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDetailResp> getQuestionDetail(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionDetail(id));
    }

    /**
     * 특정 1:1 문의에 대한 피드백을 생성합니다.
     * 
     * @param id          피드백을 생성할 문의의 ID
     * @param feedbackReq 피드백 생성 요청 정보
     * @return 성공 시 200 OK
     */
    @PostMapping("/{id}/feedback")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> createFeedback(@PathVariable Long id,
            @RequestBody @Valid CreateFeedbackReq feedbackReq) {
        questionService.createFeedback(id, feedbackReq.text());
        return ResponseEntity.ok().build();
    }
}
