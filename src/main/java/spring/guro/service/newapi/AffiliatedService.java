package spring.guro.service.newapi;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.req.SaveAffiliatedReq;
import spring.guro.entity.Affiliated;
import spring.guro.repository.newapi.AffiliatedRepo;

@Service
@RequiredArgsConstructor
public class AffiliatedService {
    private final AffiliatedRepo affiliatedRepo;

    /**
     * 새로운 제휴업체 정보를 데이터베이스에 저장합니다.
     * 
     * @param saveAffiliatedReq 저장할 제휴업체 정보를 담고 있는 DTO
     *        (카테고리, 이름, 전화번호, 위치, 내용)
     * @throws IllegalArgumentException saveAffiliatedReq가 null인 경우
     * @throws TransactionException 데이터베이스 트랜잭션 실패 시
     */
    @Transactional
    public void saveAffiliated(SaveAffiliatedReq saveAffiliatedReq) {
        Affiliated affiliated = Affiliated.builder()
                .category(saveAffiliatedReq.category())
                .name(saveAffiliatedReq.name())
                .phone(saveAffiliatedReq.phone())
                .location(saveAffiliatedReq.location())
                .content(saveAffiliatedReq.content())
                .build();
                
        affiliatedRepo.save(affiliated);
    }
}
