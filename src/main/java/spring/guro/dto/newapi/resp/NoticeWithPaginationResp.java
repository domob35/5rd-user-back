package spring.guro.dto.newapi.resp;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record NoticeWithPaginationResp(
    List<NoticeResp> notices,
    int totalPage
) {}
