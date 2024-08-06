package kafkademo.taskmanagersystem.dto.user.response;

import lombok.Data;

@Data
public class ResponseUserDto {
    private Long id;
    private String email;
    private String userName;
    private String userFirstName;
    private String userLastName;
}
