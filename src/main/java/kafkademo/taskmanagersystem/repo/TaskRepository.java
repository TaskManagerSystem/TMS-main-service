package kafkademo.taskmanagersystem.repo;

import kafkademo.taskmanagersystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
