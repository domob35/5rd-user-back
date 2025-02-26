package spring.guro.service.newapi;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Branch;
import spring.guro.entity.BranchInventory;
import spring.guro.entity.Ingredient;
import spring.guro.repository.newapi.BranchInventoryRepository;

@Service
@RequiredArgsConstructor
public class BranchInventoryService {
    private final BranchInventoryRepository branchInventoryRepository;

    /**
     * 지점의 특정 재료의 재고가 부족한지 확인하는 메소드
     * 
     * @param branch 재고를 확인할 지점
     * @param ingredient 확인할 재료
     * @param quantity 필요한 수량
     * @return 재고가 없거나 필요한 수량보다 적으면 true, 그렇지 않으면 false
     */
    public boolean isUnavailableOrNotEnough(Branch branch, Ingredient ingredient, int quantity) {
        Optional<BranchInventory> branchInventory = branchInventoryRepository.findByBranchAndIngredient(branch, ingredient);
        return branchInventory.isEmpty() || branchInventory.get().getQuantity() < quantity;
    }
}
