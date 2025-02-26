package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CartUpdateReq(
        Long cartId, // 변경할 장바구니 ID
        @NotNull(message = "주문 수량을 입력해주세요.")
        Integer quantity // 변경할 수량
) {}
