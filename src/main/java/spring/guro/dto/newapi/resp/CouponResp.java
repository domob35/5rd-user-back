package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import spring.guro.entity.Coupon;

import lombok.Builder;


// 쿠폰 데이터를 담을 DTO
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CouponResp(
        Long id, // 기본 키
        @JsonProperty("product_name")
        String productName, // 해당 쿠폰이 적용될 무료 상품 이름
        @JsonProperty("discount_price")
        Integer discountPrice // 해당 쿠폰이 적용될 무료 상품의 가격
) {
    public CouponResp(Coupon coupon) {
        this(coupon.getId(), coupon.getProduct().getName(), coupon.getProduct().getPrice());
    }
}
