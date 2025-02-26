package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import spring.guro.enumtype.Authority;

@Builder
public record LoginSuccessResp(
    String token,
    @JsonProperty("user_name")
    String userName,
    Authority authority,
    @JsonProperty("social_login")
    boolean socialLogin
) {
} 
