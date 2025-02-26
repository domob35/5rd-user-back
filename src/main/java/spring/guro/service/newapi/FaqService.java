package spring.guro.service.newapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.guro.dto.newapi.resp.FaqListResp;
import spring.guro.dto.newapi.resp.FaqResp;
import spring.guro.entity.Faq;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.FaqRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepo faqRepo;

    /**
     * 페이징된 FAQ 목록을 조회하는 메서드
     * 
     * @param pageable 페이지 정보(페이지 번호, 크기, 정렬 조건 등)를 포함하는 객체
     * @param category FAQ 카테고리 (null인 경우 전체 카테고리 조회)
     * @return FAQ 목록과 전체 페이지 수를 포함하는 FaqListResp 객체
     * @throws CustomException FAQ_NOT_FOUND - 조회된 FAQ가 없는 경우 발생
     */
    public FaqListResp getFaqs(Pageable pageable, String category) {
        Page<Faq> faqPage;

        if(category == null) {
            faqPage = faqRepo.findAll(pageable);
        } else {
            faqPage = faqRepo.findAllByCategory(category, pageable);
        }

        if (faqPage.isEmpty()) {
            throw new CustomException(ErrorEnum.FAQ_NOT_FOUND); // Faq가 비어 있을 때 CustomException 발생
        }

        // 조회된 모든 FAQ 목록을 FaqResp DTO로 변환
        List<FaqResp> faqResps = faqPage.getContent().stream()
                .map(faq -> new FaqResp(
                        faq.getId(),
                        faq.getTitle(),
                        faq.getCategory(),
                        faq.getDate(),
                        faq.getContent()
                ))
                .collect(Collectors.toList());

        // FaqListResp DTO를 생성하여 반환
        return new FaqListResp(faqResps,
                faqPage.getTotalPages());
    }
}
