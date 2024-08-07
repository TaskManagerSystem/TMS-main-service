package kafkademo.taskmanagersystem.repo;

import java.util.Optional;
import kafkademo.taskmanagersystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
