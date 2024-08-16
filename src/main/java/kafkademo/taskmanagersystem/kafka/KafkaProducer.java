package kafkademo.taskmanagersystem.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendResponseToAttachmentService(String responseMessage) {
        sendMessageToTheTopic("token-validation-response-topic", responseMessage);
    }

    private void sendMessageToTheTopic(String topic, String message) {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
            kafkaTemplate.send(record);
            log.info("Record sending: {}", record);
        } catch (Exception e) {
            log.error("Error sending response: {}", message, e);
        }
    }
}
