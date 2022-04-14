package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long recipeID;

    @JsonIgnore
    private String author;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @ElementCollection
    @NotNull(message = "Ingredients shouldn't be null")
    @Size(min = 1, message = "Minimal size should be 1")
    private List<String> ingredients;

    @ElementCollection
    @NotNull(message = "Directions shouldn't be null")
    @Size(min = 1, message = "Minimal size should be 1")
    private List<String> directions;

    @NotBlank(message = "Category is mandatory")
    private String category;

    private LocalDateTime date;
}