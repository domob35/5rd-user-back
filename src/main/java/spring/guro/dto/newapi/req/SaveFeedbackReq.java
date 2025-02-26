package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SaveFeedbackReq(
        @NotNull(message = "질문 ID는 필수입니다.")
        Long questionId, // 질문 ID

        @NotBlank(message = "내용을 입력해주세요.")
        String content // 답변 내용
) {
}
