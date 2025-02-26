package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Branch;
import spring.guro.entity.BranchInventory;
import spring.guro.entity.Ingredient;

import java.util.Optional;

public interface BranchInventoryRepository extends JpaRepository<BranchInventory, Long> {
    Optional<BranchInventory> findByBranchAndIngredient(Branch branch, Ingredient ingredient);
}
