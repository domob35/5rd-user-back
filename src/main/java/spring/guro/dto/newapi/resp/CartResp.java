package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

import java.util.List;

// 장바구니 데이터를 담는 DTO
@Builder
public record CartResp (
        List<CartProductResp> items, // 장바구니에 담긴 제품 목록
        @JsonProperty("cart_count")
        int cartCount, // 장바구니 아이템 총 개수(총 주문 수량)
        @JsonProperty("total_price")
        int totalPrice, // 장바구니 총 가격(결제 예정 금액)
        @JsonProperty("branch_id")
        long branchId, // 장바구니에 담긴 제품이 속한 지점 ID
        @JsonProperty("branch_name")
        String branchName
) {}
