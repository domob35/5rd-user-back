package spring.guro.dto.newapi.req;

import jakarta.validation.constraints.NotNull;

public record ProductsByCategoryReq(
    @NotNull(message = "브랜치의 기본키가 필요합니다.")
    Long branchId,
    String category
) {}
