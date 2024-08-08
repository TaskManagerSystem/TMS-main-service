package kafkademo.taskmanagersystem.dto.project;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class CreateProjectDto {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Long> userIds;
}
