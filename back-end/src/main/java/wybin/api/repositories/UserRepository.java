package wybin.api.repositories;

import org.springframework.data.repository.CrudRepository;
import wybin.api.models.authentication.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findById(Long id);
	boolean existsByUsername(String username);
}

