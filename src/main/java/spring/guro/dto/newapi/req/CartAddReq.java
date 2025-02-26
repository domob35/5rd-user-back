package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CartAddReq(
        @NotNull(message = "상품 ID가 필요합니다.")
        Long productId, // 장바구니에 담을 상품 ID
        @NotNull(message = "옵션 ID가 필요합니다.")
        List<Long> optionIds, // 선택한 옵션 ID
        @NotNull(message = "수량이 필요합니다.")
        Integer quantity, // 수량
        @NotNull(message = "지점 ID가 필요합니다.")
        Long branchId // 지점 ID
) {
}

