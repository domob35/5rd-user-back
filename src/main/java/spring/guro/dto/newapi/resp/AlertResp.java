package spring.guro.dto.newapi.resp;

import lombok.Builder;

@Builder
public record AlertResp(
    long id,
    String content
) {
}