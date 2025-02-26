package spring.guro.dto.newapi.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import spring.guro.constant.ValidationConstants;

public record SaveAffiliatedReq(
    @NotBlank(message = "카테고리를 입력해주세요.")
    String category,
    @NotBlank(message = "지점명을 입력해주세요.")
    String name, 
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = ValidationConstants.PHONE_NUMBER_PATTERN, message = ValidationConstants.PHONE_NUMBER_MESSAGE)
    String phone, 
    @NotBlank(message = "위치를 입력해주세요.")
    String location, 
    @NotBlank(message = "내용을 입력해주세요.")
    String content
) {
    
}
