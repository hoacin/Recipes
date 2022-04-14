package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe getRecipe(long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        return optionalRecipe.orElse(null);
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public void updateRecipe(Recipe recipe, long id) {
        Optional<Recipe> recipeOldOptional = recipeRepository.findById(id);
        if (recipeOldOptional.isPresent()) {
            Recipe recipeOld = recipeOldOptional.get();
            recipeOld.setDescription(recipe.getDescription());
            recipeOld.setName(recipe.getName());
            recipeOld.setCategory(recipe.getCategory());
            recipeOld.setDirections(recipe.getDirections());
            recipeOld.setIngredients(recipe.getIngredients());
            recipeOld.setDate(LocalDateTime.now());
        }
        recipeRepository.save(recipe);
    }

    @Override
    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findRecipeByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @Override
    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findRecipeByNameContainsIgnoreCaseOrderByDateDesc(name);
    }
}