package kafkademo.taskmanagersystem.dto.user.request;

import kafkademo.taskmanagersystem.validation.FieldMatch;
import lombok.Data;

@Data
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Password and repeatPassword should be the same!"
)
public class RegisterUserRequestDto {
    private String userName;
    private String password;
    private String repeatPassword;
    private String email;
    private String firstName;
    private String lastName;
}
