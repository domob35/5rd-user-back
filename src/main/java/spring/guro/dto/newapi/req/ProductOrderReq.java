package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import spring.guro.enumtype.PaymentMethod;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductOrderReq(
        @NotNull(message = "장바구니가 비어있습니다. 상품을 추가해주세요.")
        Long couponId, // 사용한 쿠폰 ID (nullable)
        @Min(value = 1, message = "사용한 포인트는 1 이상이어야 합니다.")
        Integer point, // 사용한 포인트 (nullable)
        @NotBlank(message = "결제 방법을 선택해주세요.")
        PaymentMethod paymentMethod // 결제 방법 (CREDIT_CARD, ACCOUNT_TRANSFER, SIMPLE_PAY)
) {
}
