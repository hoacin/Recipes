package recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import recipes.auth.WebSecurityConfigurerImpl;

@SpringBootApplication
@EnableJpaRepositories
@Import(value = WebSecurityConfigurerImpl.class)
public class RecipesApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }
}

