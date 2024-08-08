package kafkademo.taskmanagersystem.repo;

import kafkademo.taskmanagersystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    List<Project> findAllByUserId(@Param("userId") Long userId);
}
