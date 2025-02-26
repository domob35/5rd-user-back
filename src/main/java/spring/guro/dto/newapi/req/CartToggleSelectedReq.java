package spring.guro.dto.newapi.req;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record CartToggleSelectedReq(
    @NotNull(message = "장바구니 아이디는 필수 입력 값입니다.")
    List<Long> carts
) {}
