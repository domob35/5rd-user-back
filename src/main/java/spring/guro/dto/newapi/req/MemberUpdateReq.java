package spring.guro.dto.newapi.req;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberUpdateReq(
    String password,
    String phone,
    @JsonProperty("email")
    String email,
    @JsonProperty("real_name")
    String realName
) {
    
}
