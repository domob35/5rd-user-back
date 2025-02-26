package spring.guro.dto.newapi.resp;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import spring.guro.entity.Question;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuestionDetailResp (
    String title,
    String text,
    String category,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate questionDate,
    boolean answered,
    String feedbackText,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate feedbackDate
) {
    public static QuestionDetailResp from(Question question) {
        return QuestionDetailResp.builder()
            .title(question.getTitle())
            .text(question.getText())
            .category(question.getCategory())
            .questionDate(question.getDate())
            .answered(question.getFeedBack() != null)
            .feedbackText(question.getFeedBack() != null ? question.getFeedBack().getText() : null)
            .feedbackDate(question.getFeedBack() != null ? question.getFeedBack().getDate() : null) 
            .build();
    }
}
