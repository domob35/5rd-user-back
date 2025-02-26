package spring.guro.dto.newapi.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateNoticeReq(
    @NotBlank(message = "제목은 필수 입력값입니다.")
    String title,
    @NotBlank(message = "내용은 필수 입력값입니다.")
    String text
) {
    
}
