package kafkademo.taskmanagersystem.dto.user.response;

import lombok.Data;

@Data
public class ResponseUserDto {
    private Long id;
    private String email;
    private String nickName;
    private String firstName;
    private String lastName;
}
