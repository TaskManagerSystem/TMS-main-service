package kafkademo.taskmanagersystem.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRoleDto {
    @NotBlank(message = "Filed role can not be empty ")
    private String role;
}
