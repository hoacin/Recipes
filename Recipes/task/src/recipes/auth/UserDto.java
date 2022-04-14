package recipes.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @Pattern(regexp = ".+@.+\\..+")
    String email;
    @NotNull
    String password;
}
