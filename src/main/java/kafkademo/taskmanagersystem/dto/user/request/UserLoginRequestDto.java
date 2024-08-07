package kafkademo.taskmanagersystem.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank(message = "Email shouldn't be empty")
    @Size(min = 4, message = "Email should contain min 4 characters")
    private String email;
    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 4, message = "Password should contain min 4 characters")
    private String password;
}
