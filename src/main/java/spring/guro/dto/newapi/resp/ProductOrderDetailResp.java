package spring.guro.dto.newapi.resp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductOrderDetailResp(
        Long id, // 주문 번호
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime date, // 주문 일자
        String branchName, // 주문 지점명(매장명)
        String firstProductName, // 첫 번째 상품명
        int totalOrderCount, // 주문 상세 총 개수
        int billedAmount // 주문 총 결제 금액
) {}
