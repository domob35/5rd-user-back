package spring.guro.service.newapi;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Alert;
import spring.guro.entity.FeedBack;
import spring.guro.entity.Question;
import spring.guro.repository.newapi.AlertRepo;
import spring.guro.repository.newapi.FeedBackRepository;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final FeedBackRepository feedBackRepository;
    private final AlertRepo alertRepo;

    @Transactional
    public void createFromQuestion(Question question, String text) {
        FeedBack feedBack = FeedBack.builder()
            .question(question)
            .text(text)
            .build();
        StringBuilder sb = new StringBuilder();
        sb.append("'")
            .append(question.getTitle())
            .append("' 에 대한 피드백이 달렸습니다.");
        Alert alert = Alert.builder()
            .member(question.getMember())
            .content(sb.toString())
            .build();
        alertRepo.save(alert);
        feedBackRepository.save(feedBack);
    }
}
