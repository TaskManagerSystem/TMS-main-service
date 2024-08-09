package kafkademo.taskmanagersystem.repo;

import java.util.Optional;
import java.util.Set;
import kafkademo.taskmanagersystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u.id FROM User u")
    Set<Long> findAllIds();
}
