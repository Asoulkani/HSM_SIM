package ma.soultech.hsmsimTcpServer.producers;

import ma.soultech.hsmsimTcpServer.config.KafkaConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaRequestProducer {
    @Autowired
    KafkaTemplate<String, byte[]> kafkaTemplate;
    public void sendRequest(String key,byte[] req){
        kafkaTemplate.send(KafkaConfigUtils.COMMAND_REQUEST, key, req);
    }
}
