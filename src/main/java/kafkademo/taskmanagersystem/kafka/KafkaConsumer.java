package kafkademo.taskmanagersystem.kafka;

import kafkademo.taskmanagersystem.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final AuthenticationService authenticationService;

    @KafkaListener(topics = "token-validation-topic", groupId = "task-manager-systems")
    public void tokenValidate(String token) {
        authenticationService.tokenValidate(token);
    }
}
