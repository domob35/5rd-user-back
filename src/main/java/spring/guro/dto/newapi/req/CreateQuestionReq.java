package spring.guro.dto.newapi.req;

import jakarta.validation.constraints.NotBlank;

public record CreateQuestionReq(
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    String title,
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    String text,
    @NotBlank(message = "카테고리는 필수 입력 값입니다.")
    String category
) {}
