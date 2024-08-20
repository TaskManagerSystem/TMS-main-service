package kafkademo.taskmanagersystem.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
public class CreateProjectDto {
    @NotBlank
    @Size(max = 40, message = "Name can't be longer than 40 characters")
    private String name;
    @NotBlank
    @Size(max = 255, message = "Description can't be longer than 255 characters")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Set<Long> userIds;
}
