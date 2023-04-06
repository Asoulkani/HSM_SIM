package ma.soultech.hsmsimcommand.listeners;

import ma.soultech.hsmsimcommand.config.KafkaConfigUtils;
import ma.soultech.hsmsimcommand.service.CommandServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaRequestListener {
    @Autowired
    CommandServices commandServices;
    @KafkaListener(topics = KafkaConfigUtils.COMMAND_REQUEST, groupId = "Cmd-req")
    void listener(String data){
        commandServices.executeReceivedCommand(data);
    }
}
