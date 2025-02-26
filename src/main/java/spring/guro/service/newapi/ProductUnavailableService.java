package spring.guro.service.newapi;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Branch;
import spring.guro.entity.Product;
import spring.guro.entity.Recipe;
import spring.guro.repository.newapi.ProductUnavailableRepo;

@Service
@RequiredArgsConstructor
public class ProductUnavailableService {
    private final ProductUnavailableRepo productUnavailableRepo;
    private final BranchInventoryService branchInventoryService;
    private final RecipeService recipeService;

    /**
     * 특정 지점에서 상품의 판매 가능 여부를 확인합니다.
     * 다음과 같은 경우에 상품이 판매 불가능한 것으로 간주됩니다:
     * 1. ProductUnavailable 저장소에 명시적으로 판매 불가능으로 표시된 경우
     * 2. 상품 제조에 필요한 재료가 해당 지점에서 부족한 경우
     *
     * @param product 판매 가능 여부를 확인할 상품
     * @param branch 판매 가능 여부를 확인할 지점
     * @return 상품이 판매 불가능한 경우 true, 판매 가능한 경우 false 반환
     */
    public boolean isProductUnavailable(Product product, Branch branch) {
        boolean productUnavailable = productUnavailableRepo.findByProductAndBranch(product, branch).isPresent();
        List<Recipe> recipes = recipeService.getIngredientsByProduct(product);
        boolean ingredientUnavailable = recipes.stream()
                .anyMatch(recipe -> branchInventoryService.isUnavailableOrNotEnough(branch, recipe.getIngredient(), recipe.getQuantity()));
        return productUnavailable || ingredientUnavailable;
    }
}
