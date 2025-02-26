package spring.guro.service.newapi;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Option;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.OptionRepo;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepo optionRepo;

    /*
     * 옵션 ID를 기반으로 옵션 엔티티를 조회합니다.
     */
    public Option getFromId(Long id) {
        return optionRepo.findById(id).orElseThrow(() -> new CustomException(ErrorEnum.OPTION_NOT_FOUND));
    }
}
