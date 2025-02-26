package spring.guro.dto.newapi.resp;

import java.util.List;

import lombok.Builder;

@Builder
public record MemberAboutCouponResp(
    List<CouponResp> coupons,
    int stamp
) {
    
}
