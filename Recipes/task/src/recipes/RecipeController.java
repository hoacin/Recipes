package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.auth.User;
import recipes.auth.UserDto;
import recipes.service.RecipeService;
import recipes.service.UserDetailsServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserDetailsServiceImpl userDetailsService) {
        this.recipeService = recipeService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/recipe/new")
    @ResponseBody
    public ResponseEntity<Object> postRecipe(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Recipe recipe) {
        recipe.setAuthor(details.getUsername());
        Recipe newRecipe = recipeService.saveRecipe(recipe);
        return new ResponseEntity<>(Map.of("id", newRecipe.getRecipeID()), HttpStatus.OK);
    }

    @GetMapping("/recipe/{id}")
    @ResponseBody
    public ResponseEntity<Object> getRecipe(@PathVariable long id) {
        if (recipeService.getRecipe(id) == null) {
            return new ResponseEntity<>(Map.of("error", "recipe not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(recipeService.getRecipe(id), HttpStatus.OK);
    }

    @DeleteMapping("/recipe/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null)
            return new ResponseEntity<>(Map.of("error", "recipe not found"), HttpStatus.NOT_FOUND);
        if (!recipe.getAuthor().equals(details.getUsername()))
            return new ResponseEntity<>("You can't delete recipe of another user.", HttpStatus.FORBIDDEN);
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/recipe/{id}")
    @ResponseBody
    public ResponseEntity<Object> updateRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable long id, @Valid @RequestBody Recipe recipe) {
        Recipe existingRecipe = recipeService.getRecipe(id);
        if (existingRecipe == null)
            return new ResponseEntity<>(Map.of("error", "recipe not found"), HttpStatus.NOT_FOUND);
        if (!existingRecipe.getAuthor().equals(details.getUsername()))
            return new ResponseEntity<>("You can't modify recipe of another user.", HttpStatus.FORBIDDEN);
        recipeService.updateRecipe(recipe, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recipe/search")
    @ResponseBody
    public ResponseEntity<Object> searchRecipe(@RequestParam(required = false) Optional<String> category, @RequestParam(required = false) Optional<String> name) {
        if (category.isEmpty() && name.isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "only one parameter is required"), HttpStatus.BAD_REQUEST);
        } else if (category.isPresent() && name.isPresent()) {
            return new ResponseEntity<>(Map.of("error", "only one parameter is required"), HttpStatus.BAD_REQUEST);
        }
        List<Recipe> recipes = new ArrayList<>();
        if (category.isPresent()) {
            recipes = recipeService.getRecipesByCategory(category.get());
        } else if (name.isPresent()) {
            recipes = recipeService.getRecipesByName(name.get());
        }

        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Object> postRegistration(@RequestBody @Valid UserDto user) {
        if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"))
            return new ResponseEntity<>("Password is not secure.", HttpStatus.BAD_REQUEST);


        if (userDetailsService.userExists(user.getEmail())) {
            return new ResponseEntity<>("User is already in database.", HttpStatus.BAD_REQUEST);
        }
        userDetailsService.saveUser(User.FromDTO(user));
        return new ResponseEntity<>("User created successfully.", HttpStatus.OK);
    }

}