package spring.guro.dto.newapi.resp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

// 페이징된 FAQ 목록과 전체 페이지 수를 담는 DTO
public record FaqListResp(
    List<FaqResp> faqs, // FAQ 목록
    @JsonProperty("total_pages")
    int totalPages // 전체 페이지 수
) {
}
