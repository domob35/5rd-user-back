package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;

//공지사항
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record NoticeResp (
    Long id, //번호
    String title, //제목
    String text, //내용
    LocalDate date, //작성일
    Integer view //조회수
) {

}
