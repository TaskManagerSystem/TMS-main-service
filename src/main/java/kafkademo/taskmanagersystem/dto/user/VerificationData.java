package kafkademo.taskmanagersystem.dto.user;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class VerificationData {
    private String email;
    private String chatId;
    private LocalDateTime createdAt;
}
