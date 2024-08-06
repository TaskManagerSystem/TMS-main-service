package kafkademo.taskmanagersystem.dto.task;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateTaskDto {
    private String description;
    private String priority;
    private String status;
    private LocalDate dueDate;
    private Long projectId;
}
