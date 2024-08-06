package kafkademo.taskmanagersystem.repo;

import kafkademo.taskmanagersystem.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
