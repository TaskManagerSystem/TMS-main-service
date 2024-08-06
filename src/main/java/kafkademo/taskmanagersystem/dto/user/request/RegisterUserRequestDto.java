package kafkademo.taskmanagersystem.dto.user.request;

import lombok.Data;

@Data
public class RegisterUserRequestDto {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
