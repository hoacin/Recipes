package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.Recipe;
import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findRecipeByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findRecipeByNameContainsIgnoreCaseOrderByDateDesc(String name);
}