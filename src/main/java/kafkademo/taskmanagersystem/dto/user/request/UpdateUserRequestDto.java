package kafkademo.taskmanagersystem.dto.user.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @NotNull
    private String userName;
    @NotNull
    private String userFirstName;
    @NotNull
    private String userLastName;
}
