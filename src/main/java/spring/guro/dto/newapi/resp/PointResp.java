package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PointResp(
        @JsonProperty("current_point")
        int currentPoint // 사용자가 가지고 있는 포인트
) {}
