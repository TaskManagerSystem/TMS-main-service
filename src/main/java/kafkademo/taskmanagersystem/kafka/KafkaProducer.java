package kafkademo.taskmanagersystem.kafka;

import com.example.dto.IsVerificationDto;
import kafkademo.taskmanagersystem.dto.user.VerificationData;
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
        sendStringMessageToTheTopic("token-validation-response-topic", dto);
    }

    public void sendVerificationData(String token, VerificationData verificationData) {
        try {
            ProducerRecord<String, Object> record =
                    new ProducerRecord<>("email-validation-response-topic",
                            token,
                            verificationData);
            kafkaTemplate.send(record);
        } catch (Exception e) {
            log.error("Error sending response: {}", verificationData, e);
        }
    }

    private void sendStringMessageToTheTopic(String topic, IsVerificationDto dto) {
        try {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, dto);
            kafkaTemplate.send(record);
            log.info("Record sending: {}", record);
        } catch (Exception e) {
            log.error("Error sending response: {}", dto, e);
        }
    }
}
