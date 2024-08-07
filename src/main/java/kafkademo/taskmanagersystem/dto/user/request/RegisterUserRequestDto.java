package kafkademo.taskmanagersystem.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kafkademo.taskmanagersystem.validation.FieldMatch;
import lombok.Data;

@Data
@FieldMatch(field = "password",
        fieldMatch = "repeatPassword",
        message = "Password and repeatPassword should be the same!")
public class RegisterUserRequestDto {
    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 16)
    private String password;

    @NotBlank(message = "Repeated password cannot be blank")
    private String repeatPassword;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
}
