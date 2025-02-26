package spring.guro.dto.newapi.resp;

import java.time.LocalDate;

public record FeedBackDetailResp(
        Long questionId, // 질문 ID
        String title, // 질문 제목
        String category, // 질문 카테고리
        LocalDate date, // 질문 날짜
        boolean isAnswered // 답변 여부
) {
}
