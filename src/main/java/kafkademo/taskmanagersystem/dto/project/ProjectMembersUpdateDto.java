package kafkademo.taskmanagersystem.dto.project;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class ProjectMembersUpdateDto {
    @NotNull
    private Set<Long> memberIds;
}
