package spring.guro.dto.newapi.resp;

import java.util.List;

import lombok.Builder;

@Builder
public record PersonalProductResp(
    long id,
    ProductResp product,
    List<OptionResp> options,
    String name,
    int price
) {
    
}
