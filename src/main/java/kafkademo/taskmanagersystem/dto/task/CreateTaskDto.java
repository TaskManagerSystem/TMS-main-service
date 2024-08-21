package kafkademo.taskmanagersystem.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateTaskDto {
    @NotBlank
    @Size(max = 40, message = "Name can't be longer than 30 characters")
    private String name;
    @NotBlank
    @Size(max = 255, message = "Description can't be longer than 40 characters")
    private String description;
    @NotBlank
    private String priority;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    @NotBlank
    private Long projectId;
    @NotBlank
    private Long userId;
}
