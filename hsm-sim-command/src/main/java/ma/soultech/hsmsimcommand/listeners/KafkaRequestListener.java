package ma.soultech.hsmsimcommand.listeners;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimcommand.config.KafkaConfigUtils;
import ma.soultech.hsmsimcommand.service.CommandServices;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaRequestListener {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CommandServices commandServices;
    @KafkaListener(topics = KafkaConfigUtils.COMMAND_REQUEST, groupId = "Cmd-req")
    void listener(ConsumerRecord<String, byte[]> record){
        logger.info("UUID : {} | Received data : {}",record.key(), HsmDataConverter.printCommandFromByte(record.value()));
        commandServices.executeReceivedCommand(record.key(), record.value());
    }
}
