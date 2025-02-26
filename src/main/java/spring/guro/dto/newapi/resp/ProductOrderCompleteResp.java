package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record ProductOrderCompleteResp(
        long id, // 주문 번호
        String branchName, // 주문 지점명
        int totalPrice, // 총 주문 금액
        int billedPrice // 청구 금액
) {}
