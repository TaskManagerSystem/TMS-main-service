package kafkademo.taskmanagersystem.dto.project;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Set<Long> userIds;
}
