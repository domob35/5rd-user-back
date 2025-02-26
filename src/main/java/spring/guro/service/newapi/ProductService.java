package spring.guro.service.newapi;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.dto.newapi.resp.OptionResp;
import spring.guro.dto.newapi.resp.ProductResp;
import spring.guro.dto.newapi.resp.ProductWithAvailabilityResp;
import spring.guro.entity.Branch;
import spring.guro.entity.Option;
import spring.guro.entity.Product;
import spring.guro.exception.CustomException;
import spring.guro.exception.ErrorEnum;
import spring.guro.repository.newapi.OptionRepo;
import spring.guro.repository.newapi.ProductOptionRepo;
import spring.guro.repository.newapi.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final BranchService branchService;
    private final ProductUnavailableService productUnavailableService;
    private final ProductRepository productRepository;
    private final ProductOptionRepo productOptionRepo;
    private final OptionRepo optionRepo;

    /**
     * 특정 지점의 카테고리별 상품 목록을 조회합니다.
     *
     * @param BranchId 조회할 지점의 ID
     * @param category 조회할 상품의 카테고리
     * @return 해당 카테고리의 상품 목록을 ProductWithAvailabilityResp DTO 리스트 형태로 반환
     * @throws CustomException BRANCH_NOT_FOUND (404) - 해당 지점이 존재하지 않는 경우
     */
    public List<ProductWithAvailabilityResp> getProductsByCategory(long BranchId, String category) {
        Branch branch = branchService.getBranchById(BranchId);
        List<Product> products = productRepository.findAllByCategory(category);

        if(products.isEmpty()) {
            throw new CustomException(ErrorEnum.PRODUCT_EMPTY);
        }
        
        List<ProductWithAvailabilityResp> productByCategoryResps = products.stream().map(
                product -> {
                    var productResp = new ProductResp(product.getId(), product.getName(), product.getDesc(),
                            product.getImage(), product.getPrice(),
                            product.getCalories(),
                            product.getCarbohydrates(), product.getProtein(), product.getFat(), product.getCaffeine());
                    return new ProductWithAvailabilityResp(productResp,
                            productUnavailableService.isProductUnavailable(product, branch));
                }).toList();
        return productByCategoryResps;
    }

    /**
     * 특정 지점에서 선택된 상품의 옵션 조회
     */
    public List<OptionResp> getProductOptions(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorEnum.PRODUCT_EMPTY));

        // 선택 가능한 옵션 조회
        List<Option> options = productOptionRepo.findByProduct(product).stream().map(productOption -> productOption.getOption()).toList();

        // 만약 선택 가능한 옵션이 없다면 모든 옵션 부여
        if(options.isEmpty()) {
            options = optionRepo.findAll();
        }

        List<OptionResp> availableOptions = options.stream().map(option -> new OptionResp(option.getId(), option.getName(), option.getPrice(), option.getGroup())).toList();

        return availableOptions;

    }

    /*
     * 상품 ID로 상품 조회
     */
    public Product getFromId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorEnum.PRODUCT_EMPTY));
    }
}
