package kafkademo.taskmanagersystem.repo;

import java.util.List;
import kafkademo.taskmanagersystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectId(Long projectId);
}
