package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@JsonNaming(SnakeCaseStrategy.class)
@Builder
public record MemberAboutMembershipResp(
    String membershipName,
    int point,
    double pointRate,
    long totalPaymentAmount,
    int totalPurchaseCount
) {
    
}
