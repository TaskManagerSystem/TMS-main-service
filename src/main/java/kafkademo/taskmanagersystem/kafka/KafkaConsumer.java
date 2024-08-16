package kafkademo.taskmanagersystem.kafka;

import java.time.LocalDateTime;
import kafkademo.taskmanagersystem.dto.user.VerificationData;
import kafkademo.taskmanagersystem.security.AuthenticationService;
import kafkademo.taskmanagersystem.validation.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final AuthenticationService authenticationService;
    private final VerificationService verificationService;

    @KafkaListener(topics = "token-validation-topic", groupId = "task-manager-systems")
    public void tokenValidate(String token) {
        authenticationService.tokenValidate(token);
    }

    @KafkaListener(topics = "email-validation-topic", groupId = "task-manager-systems")
    public void emailValidate(String value) {
        VerificationData verificationData = new VerificationData();
        verificationData.setCreatedAt(LocalDateTime.now());
        String token = value.split(":")[0];
        verificationData.setEmail(value.split(":")[1]);
        verificationData.setChatId(value.split(":")[2]);
        verificationService.saveVerificationData(token, verificationData);
    }
}
