package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record OptionResp(
        Long id, // 옵션 ID
        String name, // 옵션 이름
        Integer price, // 옵션 가격
        String group // 옵션 그룹
) {
}
