package ma.soultech.hsmsimcommand.producers;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimcommand.config.KafkaConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaResponseProducer {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    KafkaTemplate<String, byte[]> kafkaTemplate;
    public void sendResponse(String key, byte[] response){
        kafkaTemplate.send(KafkaConfigUtils.COMMAND_RESPONSE, key, response);
        logger.info("UUID : {} | sending responce : {} | to topic {}", key, HsmDataConverter.printCommandFromByte(response), KafkaConfigUtils.COMMAND_RESPONSE);
    }
}
