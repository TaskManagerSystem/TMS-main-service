package kafkademo.taskmanagersystem.repo;

import java.util.Set;
import kafkademo.taskmanagersystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByRoleName(Role.RoleName roleName);
}
