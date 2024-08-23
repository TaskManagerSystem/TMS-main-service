package kafkademo.taskmanagersystem.kafka;

import com.example.dto.IsVerificationDto;
import com.example.dto.VerificationData;
import kafkademo.taskmanagersystem.repo.UserRepository;
import kafkademo.taskmanagersystem.security.AuthenticationService;
import kafkademo.taskmanagersystem.validation.VerificationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private final AuthenticationService authenticationService;
    private final VerificationService verificationService;
    private final UserRepository userRepository;
    private final KafkaProducer producer;

    @KafkaListener(topics = "token-validation-topic", groupId = "task-manager-systems")
    public void tokenValidate(IsVerificationDto dto) {
        authenticationService.tokenValidate(dto);
    }

    @KafkaListener(topics = "email-validation-topic", groupId = "task-manager-systems")
    public void emailValidate(ConsumerRecord<String, VerificationData> record) {
        String token = record.key();
        VerificationData verificationData = record.value();
        boolean isPresent =
                userRepository.findUserByEmail(verificationData.getEmail()).isPresent();
        verificationData.setPresent(isPresent);
        if (isPresent) {
            verificationService.saveVerificationData(token, verificationData);
        }
        producer.sendVerificationData(token, verificationData);
    }
}
