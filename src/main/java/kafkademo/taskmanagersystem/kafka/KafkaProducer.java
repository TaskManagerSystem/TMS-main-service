package kafkademo.taskmanagersystem.kafka;

import kafkademo.taskmanagersystem.dto.NotificationData;
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
    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, VerificationData> verificationDataKafkaTemplate;
    private final KafkaTemplate<String, NotificationData> notificationDataKafkaTemplate;

    public void sendResponseToAttachmentService(String responseMessage) {
        sendStringMessageToTheTopic("token-validation-response-topic", responseMessage);
    }

    public void sendVerificationData(String token, VerificationData verificationData) {
        try {
            ProducerRecord<String, VerificationData> record =
                    new ProducerRecord<>("email-validation-response-topic",
                            token,
                            verificationData);
            verificationDataKafkaTemplate.send(record);
        } catch (Exception e) {
            log.error("Error sending response: {}", verificationData, e);
        }
    }

    public void sendNotificationData(NotificationData notificationData) {
        try {
            ProducerRecord<String, NotificationData> record =
                    new ProducerRecord<>("email-validation-response-topic",
                            notificationData);
            notificationDataKafkaTemplate.send(record);
        } catch (Exception e) {
            log.error("Error sending response: {}", notificationData, e);
        }
    }

    private void sendStringMessageToTheTopic(String topic, String message) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            stringKafkaTemplate.send(record);
            log.info("Record sending: {}", record);
        } catch (Exception e) {
            log.error("Error sending response: {}", message, e);
        }
    }
}
