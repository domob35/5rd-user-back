package spring.guro.dto.newapi.req;

import jakarta.validation.constraints.NotBlank;

public record LoginReq(
    @NotBlank(message = "아이디를 입력해주세요.")
    String userName,
    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password
) {
    
}
