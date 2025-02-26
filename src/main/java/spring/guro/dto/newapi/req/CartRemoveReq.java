package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CartRemoveReq(
        List<Long> cartIds // 장바구니에서 제거할 상품 ID 목록
) {
}
