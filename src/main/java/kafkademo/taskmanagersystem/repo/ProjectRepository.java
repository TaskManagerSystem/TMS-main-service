package kafkademo.taskmanagersystem.repo;

import kafkademo.taskmanagersystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
