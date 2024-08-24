package kafkademo.taskmanagersystem.repo;

import java.time.LocalDate;
import java.util.List;
import kafkademo.taskmanagersystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectId(Long projectId);

    @Query("SELECT t FROM Task t "
            + "JOIN FETCH t.user "
            + "JOIN FETCH t.project "
            + "WHERE t.dueDate = :today "
            + "AND t.status != com.example.Project.Status.COMPLETED")
    List<Task> findTasksWithDueDateTodayAndNotCompleted(@Param("today") LocalDate today);
}
