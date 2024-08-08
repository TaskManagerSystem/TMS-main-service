package kafkademo.taskmanagersystem.repo;

import java.util.List;
import kafkademo.taskmanagersystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);
}
