package spring.guro.service.newapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.req.CreateQuestionReq;
import spring.guro.dto.newapi.resp.QuestionListResp;
import spring.guro.dto.newapi.resp.QuestionDetailResp;
import spring.guro.entity.Member;
import spring.guro.entity.Question;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.QuestionRepo;

import java.time.LocalDate;

/**
 * 1:1 문의 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final AuthService authService;
    private final FeedBackService feedBackService;

    /**
     * 새로운 1:1 문의를 생성하고 저장합니다.
     * 
     * @param req 문의 생성 요청 정보
     */
    public void createQuestion(CreateQuestionReq req) {
        Member member = authService.getLoginMember();
        Question question = Question.builder()
                .member(member)
                .title(req.title())
                .text(req.text())
                .category(req.category())
                .build();
        questionRepo.save(question);
    }

    /**
     * 로그인한 사용자의 1:1 문의 내역을 조회합니다.
     * 날짜 범위가 지정되지 않은 경우 기본값을 적용합니다.
     * 
     * @param startDate 조회 시작 날짜 (null인 경우 오늘)
     * @param endDate   조회 종료 날짜 (null인 경우 7일 전)
     * @param pageable  페이지네이션 정보
     * @return 문의 목록과 전체 페이지 수가 포함된 응답
     */
    public QuestionListResp getQuestionsFromMember(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Member member = authService.getLoginMember();
        LocalDate now = LocalDate.now();

        // 날짜 기본값 설정: startDate가 null이면 오늘, endDate가 null이면 7일 전
        LocalDate effectiveStartDate = startDate != null ? startDate : now;
        LocalDate effectiveEndDate = endDate != null ? endDate : now.minusDays(7);

        // 회원의 문의 내역을 날짜 범위로 조회
        Page<Question> questionPage = questionRepo.findAllByMemberAndDateBetweenOrderByDateDesc(
                member, effectiveStartDate, effectiveEndDate, pageable);

        // 조회 결과를 DTO로 변환하여 반환
        return buildQuestionListResp(questionPage);
    }

    /**
     * 로그인한 사용자의 1:1 문의 내역을 조회합니다.
     * 날짜 범위가 지정되지 않은 경우 기본값을 적용합니다.
     * 
     * @param startDate 조회 시작 날짜 (null인 경우 오늘)
     * @param endDate   조회 종료 날짜 (null인 경우 7일 전)
     * @param pageable  페이지네이션 정보
     * @return 문의 목록과 전체 페이지 수가 포함된 응답
     */
    public QuestionListResp getQuestions(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        LocalDate now = LocalDate.now();

        // 날짜 기본값 설정: startDate가 null이면 오늘, endDate가 null이면 7일 전
        LocalDate effectiveStartDate = startDate != null ? startDate : now;
        LocalDate effectiveEndDate = endDate != null ? endDate : now.minusDays(7);

        // 회원의 문의 내역을 날짜 범위로 조회
        Page<Question> questionPage = questionRepo.findAllByDateBetweenOrderByDateDesc(effectiveStartDate,
                effectiveEndDate, pageable);

        // 조회 결과를 DTO로 변환하여 반환
        return buildQuestionListResp(questionPage);
    }

    /**
     * 주어진 페이지의 질문 목록을 기반으로 QuestionListResp 객체를 생성합니다.
     *
     * @param questionPage 질문 목록을 포함하는 페이지 객체
     * @return 질문 목록과 전체 페이지 수가 포함된 QuestionListResp 객체
     */
    private QuestionListResp buildQuestionListResp(Page<Question> questionPage) {
        return QuestionListResp.builder()
                .questions(questionPage.getContent().stream()
                        .map(QuestionListResp.QuestionResp::from)
                        .toList())
                .totalPage(questionPage.getTotalPages())
                .build();
    }

    /**
     * 특정 1:1 문의의 상세 내용을 조회합니다.
     * 
     * @param id 조회할 문의의 ID
     * @return 문의 상세 정보
     * @throws CustomException QUESTION_NOT_FOUND (404) - 해당 문의가 존재하지 않는 경우
     */
    public QuestionDetailResp getQuestionDetail(Long id) {
        Question question = questionRepo.findById(id)
                .orElseThrow(() -> new CustomException(ErrorEnum.QUESTION_NOT_FOUND));

        return QuestionDetailResp.from(question);
    }

    /**
     * 주어진 질문 ID를 기반으로 피드백을 생성합니다.
     * 
     * @param questionId 피드백을 생성할 질문의 ID
     * @param text       피드백 내용
     * @throws CustomException QUESTION_NOT_FOUND (404) - 해당 질문이 존재하지 않는 경우
     */
    public void createFeedback(Long questionId, String text) {
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new CustomException(ErrorEnum.QUESTION_NOT_FOUND));

        feedBackService.createFromQuestion(question, text);
    }
}
