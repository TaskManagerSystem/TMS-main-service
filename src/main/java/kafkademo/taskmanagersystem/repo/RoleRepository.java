package kafkademo.taskmanagersystem.repo;

import java.util.Optional;
import kafkademo.taskmanagersystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findAllByRoleName(Role.RoleName roleName);
}
