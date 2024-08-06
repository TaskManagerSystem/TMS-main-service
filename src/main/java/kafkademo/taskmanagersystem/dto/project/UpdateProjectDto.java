package kafkademo.taskmanagersystem.dto.project;

import java.time.LocalDate;
import lombok.Data;

@Data
public class UpdateProjectDto {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
