package recipes.service;

import recipes.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe getRecipe(long id);

    Recipe saveRecipe(Recipe recipe);

    void deleteRecipe(long id);

    void updateRecipe(Recipe recipe, long id);

    List<Recipe> getRecipesByCategory(String category);

    List<Recipe> getRecipesByName(String name);
}