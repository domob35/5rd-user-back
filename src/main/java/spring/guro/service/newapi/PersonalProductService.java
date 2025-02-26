package spring.guro.service.newapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.resp.OptionResp;
import spring.guro.dto.newapi.resp.PersonalProductResp;
import spring.guro.dto.newapi.resp.ProductResp;
import spring.guro.entity.Member;
import spring.guro.entity.Option;
import spring.guro.entity.PersonalProduct;
import spring.guro.entity.PersonalProductOption;
import spring.guro.entity.Product;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.PersonalProductRepo;

@Service
@RequiredArgsConstructor
public class PersonalProductService {
    private final PersonalProductRepo personalProductRepo;

    /**
     * 제품을 기반으로 회원의 개인 맞춤 제품을 생성하고 저장합니다.
     *
     * @param product 개인 맞춤 제품의 기본이 되는 제품
     * @param member 개인 맞춤 제품을 소유할 회원
     * @param name 개인 맞춤 제품의 이름
     * @return 저장된 PersonalProduct 엔티티
     */
    @Transactional
    public PersonalProduct saveFromProduct(Product product, Member member, String name) {
        PersonalProduct pp = PersonalProduct.builder()
            .name(name)
            .member(member)
            .product(product)
            .build();
        return personalProductRepo.save(pp);
    }

    /**
     * 특정 회원의 모든 개인 맞춤 제품 목록을 조회합니다.
     * 
     * @param member 조회할 회원 엔티티
     * @return 회원의 개인 맞춤 제품 목록을 PersonalProductResp DTO 리스트로 변환하여 반환
     * @throws CustomException ErrorEnum.PERSONAL_PRODUCT_EMPTY - 회원의 개인 맞춤 제품이 없는 경우 발생
     * 
     * @implNote 이 메서드는 다음과 같은 처리를 수행합니다:
     * - 회원의 모든 개인 맞춤 제품을 데이터베이스에서 조회
     * - 조회된 결과가 없으면 PERSONAL_PRODUCT_EMPTY 예외 발생
     * - 각 개인 맞춤 제품을 DTO로 변환 (제품 정보, 옵션 정보 포함)
     */
    public List<PersonalProductResp> listPersonalProductFromMember(Member member) {
        List<PersonalProduct> personalProducts = personalProductRepo.findAllByMember(member);
        if(personalProducts.isEmpty()) {
            throw new CustomException(ErrorEnum.PERSONAL_PRODUCT_EMPTY);
        }

        List<PersonalProductResp> personalProductResps = personalProducts.stream()
            .map(this::toDto).toList();
        
        return personalProductResps;
    }

    /**
     * PersonalProduct 엔티티를 PersonalProductResp DTO로 변환합니다.
     * 
     * @param pp 변환할 PersonalProduct 엔티티
     * @return 변환된 PersonalProductResp DTO
     * 
     * @implNote 이 메서드는 다음 정보를 변환합니다:
     * - 제품 정보 (기본키, 이름, 설명, 이미지 URL, 가격)
     * - 선택된 옵션 정보 (이름, 가격)
     * - 개인 맞춤 제품 정보 (ID, 이름)
     * - 최종 가격 (제품 가격 + 옵션 가격)
     */
    private PersonalProductResp toDto(PersonalProduct pp) {
        Product product = pp.getProduct();
        ProductResp productResp = ProductResp.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDesc())
                    .imageUrl(product.getImage())
                    .price(product.getPrice())
                    .build();
        int price = product.getPrice();

        // 옵션 정보 변환
        List<OptionResp> options = new ArrayList<>();
        for(PersonalProductOption ppOption : pp.getPersonalProductOptions()) {
            Option option = ppOption.getOption();
            price += option.getPrice();
            options.add(OptionResp.builder()
                .id(option.getId())
                .name(option.getName())
                .price(option.getPrice())
                .group(option.getGroup())
                .build());
        }

        return PersonalProductResp.builder()
            .id(pp.getId())
            .name(pp.getName())
            .product(productResp)
            .options(options)
            .price(price)
            .build();
    }

    @Transactional
    public void delete(long id) {
        PersonalProduct pp = personalProductRepo.findById(id)
            .orElseThrow(() -> new CustomException(ErrorEnum.PERSONAL_PRODUCT_NOT_FOUND));
        personalProductRepo.delete(pp);
    }
}
