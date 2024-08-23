package kafkademo.taskmanagersystem.kafka;

import com.example.dto.IsVerificationDto;
import com.example.dto.NotificationData;
import com.example.dto.VerificationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendResponseToAttachmentService(IsVerificationDto dto) {
        sendMessageToTheTopic("token-validation-response-topic", dto);
    }

    public void sendVerificationData(String token, VerificationData verificationData) {
        sendMessageToTheTopic("email-validation-response-topic", token, verificationData);
    }

    public void sendNotificationData(NotificationData notificationData) {
        sendMessageToTheTopic("notification-topic", notificationData);
    }

    private <V> void sendMessageToTheTopic(String topic, String key, V value) {
        try {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, key, value);
            kafkaTemplate.send(record);
            log.info("Record sent: {}:{}", record.key(), record.value());
        } catch (Exception e) {
            log.error("Error sending response: {}:{}", key, value, e);
        }
    }

    private <V> void sendMessageToTheTopic(String topic, V value) {
        try {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, value);
            kafkaTemplate.send(record);
            log.info("Record sent: {}", record.value());
        } catch (Exception e) {
            log.error("Error sending response: {}", value, e);
        }
    }
}
