package kafkademo.taskmanagersystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationData {
    private String email;
    private String chatId;
    private String message;
    private String topic;
}
