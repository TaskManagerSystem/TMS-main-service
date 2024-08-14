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
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "token-validation-response-topic", responseMessage);
            log.info("Record sending: " + record);
            kafkaTemplate.send(record);
        } catch (Exception e) {
            log.error("Error sending response:" + responseMessage, e);
        }
    }
}
