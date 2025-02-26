package spring.guro.service.newapi;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.guro.entity.Product;
import spring.guro.entity.Recipe;
import spring.guro.repository.newapi.RecipeRepository;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> getIngredientsByProduct(Product product) {
        return recipeRepository.findAllByProduct(product);
    }
}
