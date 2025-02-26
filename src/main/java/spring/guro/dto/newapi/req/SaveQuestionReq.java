package spring.guro.dto.newapi.req;

import jakarta.validation.constraints.NotBlank;

public record SaveQuestionReq (
        @NotBlank(message = "카테고리를 입력해주세요.")
        String category,
        @NotBlank(message = "제목을 입력해주세요.")
        String title,
        @NotBlank(message = "내용을 입력해주세요.")
        String content
) {

}
