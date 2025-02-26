package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Builder;

// 장바구니에 당긴 제품 데이터를 담을 DTO
@Builder
public record CartProductResp(
        @JsonProperty("cart_id")
        long cartId, // 기본 키
        @JsonProperty("product_id")
        long productId, // 제품 기본 키
        String name, // 제품명
        int price, // 가격
        int quantity, // 수량
        @JsonProperty("image_url")
        String imageUrl, // 제품 이미지 경로
        List<OptionResp> options, // 선택된 옵션들
        @JsonProperty("total_item_price")
        int totalItemPrice, // 제품+옵션 총 가격
        boolean selected // 장바구니 아이템 선택 여부
) {}