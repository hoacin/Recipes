package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.auth.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmailIgnoreCase(String email);
}