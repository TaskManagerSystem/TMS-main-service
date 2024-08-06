package kafkademo.taskmanagersystem.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String userFirstName;
    @NotBlank
    private String userLastName;
}
