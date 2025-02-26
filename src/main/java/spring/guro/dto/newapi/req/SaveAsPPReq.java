package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SaveAsPPReq(
    @NotNull(message = "제품 ID는 필수입니다.")
    Long productId,
    @NotNull(message = "옵션 ID는 필수입니다.")
    List<Long> optionIds,
    @NotBlank(message = "개인상품 이름은 필수입니다.")
    String name
) {
    
}
