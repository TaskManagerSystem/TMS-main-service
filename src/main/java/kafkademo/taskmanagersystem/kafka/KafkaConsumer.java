package kafkademo.taskmanagersystem.kafka;

import java.time.LocalDateTime;
import kafkademo.taskmanagersystem.dto.user.VerificationData;
import kafkademo.taskmanagersystem.repo.UserRepository;
import kafkademo.taskmanagersystem.security.AuthenticationService;
import kafkademo.taskmanagersystem.validation.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumer {
    private static final String SEPARATOR = ":";
    private static final int TOKEN_INDEX = 0;
    private static final int EMAIL_INDEX = 1;
    private static final int CHAT_ID_INDEX = 2;
    private final AuthenticationService authenticationService;
    private final VerificationService verificationService;
    private final UserRepository userRepository;
    private final KafkaProducer producer;

    @KafkaListener(topics = "token-validation-topic", groupId = "task-manager-systems")
    public void tokenValidate(String token) {
        authenticationService.tokenValidate(token);
    }

    @KafkaListener(topics = "email-validation-topic", groupId = "task-manager-systems")
    public void emailValidate(String value) {
        VerificationData verificationData = new VerificationData();
        verificationData.setCreatedAt(LocalDateTime.now());
        String token = value.split(SEPARATOR)[TOKEN_INDEX];
        verificationData.setEmail(value.split(SEPARATOR)[EMAIL_INDEX]);
        verificationData.setChatId(value.split(SEPARATOR)[CHAT_ID_INDEX]);
        boolean isPresent =
                userRepository.findUserByEmail(verificationData.getEmail()).isPresent();
        verificationData.setPresent(isPresent);
        if (isPresent) {
            verificationService.saveVerificationData(token, verificationData);
        }
        producer.sendVerificationData(token, verificationData);
    }
}
