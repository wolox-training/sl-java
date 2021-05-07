package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Find a user by its username
     *
     * @param username: username assigning the user (String)
     * @return First user with the username
     */

    Optional<User> findFirstByUsername(String username);
}
