package wolox.training.repositories;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by its username
     *
     * @param username: username assigning the user (String)
     *
     * @return First user with the username
     */

    Optional<User> findFirstByUsername(String username);

    @Query("SELECT u FROM users u "
            + "WHERE (:startDate IS NULL OR u.birthdate >= :startDate) "
            + "AND (:endDate IS NULL OR u.birthdate <= :endDate) "
            + "AND (:partName IS NULL OR LOWER(u.name) LIKE CONCAT('%',LOWER(:partName),'%'))")
    Page<User> findAllByBirthdateBetweenAndNameIgnoreCaseContaining(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate, @Param("partName") String partName, Pageable pageable);
}
