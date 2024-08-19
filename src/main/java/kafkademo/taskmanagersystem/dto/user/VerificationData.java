package kafkademo.taskmanagersystem.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class VerificationData {
    private String email;
    private String chatId;
    @JsonIgnore
    private LocalDateTime createdAt;
    private boolean present;
}
