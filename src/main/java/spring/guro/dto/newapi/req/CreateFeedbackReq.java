package spring.guro.dto.newapi.req;

import jakarta.validation.constraints.NotBlank;

public record CreateFeedbackReq(
        @NotBlank(message = "피드백 내용은 필수 입력값입니다.") String text) {

}
