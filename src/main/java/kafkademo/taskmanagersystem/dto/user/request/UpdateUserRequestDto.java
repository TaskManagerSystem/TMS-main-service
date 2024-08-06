package kafkademo.taskmanagersystem.dto.user.request;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String userName;
    private String userFirstName;
    private String userLastName;
}
