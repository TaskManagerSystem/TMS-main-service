package kafkademo.taskmanagersystem.dto.project;

import java.util.Set;
import lombok.Data;

@Data
public class ProjectMembersUpdateDto {
    private Set<Long> memberIds;
}
