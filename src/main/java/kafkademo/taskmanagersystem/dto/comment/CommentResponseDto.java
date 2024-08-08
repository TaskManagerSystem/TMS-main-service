package kafkademo.taskmanagersystem.dto.comment;

import java.time.LocalDateTime;
import kafkademo.taskmanagersystem.entity.Task;
import kafkademo.taskmanagersystem.entity.User;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private Task task;
    private User user;
    private String text;
    private LocalDateTime timestamp;
}
