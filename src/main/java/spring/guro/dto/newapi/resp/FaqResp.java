package spring.guro.dto.newapi.resp;

import java.time.LocalDate;

// FAQ 정보를 담는 DTO
public record FaqResp(
    long id, // 기본 키
    String title, // 제목
    String category, // 카테고리
    LocalDate date, // 작성일
    String content // 내용
) {}
