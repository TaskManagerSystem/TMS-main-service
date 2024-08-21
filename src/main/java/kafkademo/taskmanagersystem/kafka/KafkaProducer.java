package kafkademo.taskmanagersystem.kafka;

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

    public void sendResponseToAttachmentService(String responseMessage) {
        sendStringMessageToTheTopic("token-validation-response-topic", responseMessage);
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

    private void sendStringMessageToTheTopic(String topic, String message) {
        try {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topic, message);
            kafkaTemplate.send(record);
            log.info("Record sending: {}", record);
        } catch (Exception e) {
            log.error("Error sending response: {}", message, e);
        }
    }
}
