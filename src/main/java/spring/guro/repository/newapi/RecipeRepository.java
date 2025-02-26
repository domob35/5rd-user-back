package spring.guro.repository.newapi;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.guro.entity.Product;
import spring.guro.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByProduct(Product product);

    Optional<Recipe> findByProduct(Product product);
}
