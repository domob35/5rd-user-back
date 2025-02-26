package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import spring.guro.constant.ValidationConstants;

@Builder
public record SignUpReq(
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    String email,
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    String password,
    @JsonProperty("user_name")
    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Size(min = 2, message = "사용자 이름은 2자 이상이어야 합니다.")
    String userName,
    @JsonProperty("real_name")
    @NotBlank(message = "실명을 입력해주세요.")
    @Size(min = 2, max = 4, message = "실명은 2자 이상 4자 이하이어야 합니다.")
    String realName,
    @Pattern(regexp = ValidationConstants.PHONE_NUMBER_PATTERN, message = ValidationConstants.PHONE_NUMBER_MESSAGE)
    String phone
) {
    
}
