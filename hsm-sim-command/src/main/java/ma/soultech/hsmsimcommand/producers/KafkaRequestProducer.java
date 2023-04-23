package ma.soultech.hsmsimcommand.producers;

import ma.soultech.hsmsimcommand.config.KafkaConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaRequestProducer {
    @Autowired
    KafkaTemplate<String, byte[]> kafkaTemplate;
    public void sendRequest(String key, byte[] request){
        kafkaTemplate.send(KafkaConfigUtils.COMMAND_REQUEST, key, request);
    }
}
