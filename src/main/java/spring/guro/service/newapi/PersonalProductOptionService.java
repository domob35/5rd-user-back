package spring.guro.service.newapi;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Option;
import spring.guro.entity.PersonalProduct;
import spring.guro.entity.PersonalProductOption;
import spring.guro.repository.newapi.PersonalProductOptionRepo;

@Service
@RequiredArgsConstructor
public class PersonalProductOptionService {
    private final PersonalProductOptionRepo personalProductOptionRepo;

    /**
     * 주어진 옵션과 개인 제품을 기반으로 새로운 개인 제품 옵션 엔티티를 생성하고 저장합니다.
     *
     * @param option 개인 제품 옵션과 연결할 옵션 엔티티
     * @param personalProduct 개인 제품 옵션과 연결할 개인 제품 엔티티
     * 
     * @implNote 이 메서드는 트랜잭션 컨텍스트 내에서 동작합니다
     */
    @Transactional
    public void saveFromOption(Option option, PersonalProduct personalProduct) {
        personalProductOptionRepo.save(PersonalProductOption.builder()
            .personalProduct(personalProduct)
            .option(option)
            .build());
    }
}
