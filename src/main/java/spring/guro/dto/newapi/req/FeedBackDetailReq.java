package spring.guro.dto.newapi.req;

import java.time.LocalDate;

public record FeedBackDetailReq(
        LocalDate startDate, // 시작 날짜
        LocalDate endDate // 종료 날짜
) {
}
