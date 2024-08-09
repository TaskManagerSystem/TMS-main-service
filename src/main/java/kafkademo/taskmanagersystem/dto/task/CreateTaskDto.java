package kafkademo.taskmanagersystem.dto.task;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateTaskDto {
    private String name;
    private String description;
    private String priority;
    private LocalDate dueDate;
    private Long projectId;
    private Long userId;
}
