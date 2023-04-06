package ma.soultech.hsmsimcommand.producers;

import ma.soultech.hsmsimcommand.config.KafkaConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaResponseProducer {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    public void sendResponse(String response){
        kafkaTemplate.send(KafkaConfigUtils.COMMAND_RESPONSE, response);
    }
}
