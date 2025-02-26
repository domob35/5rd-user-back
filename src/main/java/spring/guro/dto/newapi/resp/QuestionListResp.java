package spring.guro.dto.newapi.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import spring.guro.entity.Question;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record QuestionListResp(
    List<QuestionResp> questions,
    int totalPage
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Builder
    public static record QuestionResp(
        long id,
        String title,
        String text,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate createdDate,
        boolean answered
    ) {
        public static QuestionResp from(Question question) {
            return QuestionResp.builder()
                .id(question.getId())
                .title(question.getTitle())
                .text(question.getText())
                .createdDate(question.getDate())
                .answered(question.getFeedBack() != null)
                .build();
        }
    }
}
