package ma.soultech.hsmsimcommand.listeners;

import ma.soultech.hsmsimcommand.config.KafkaConfigUtils;
import ma.soultech.hsmsimcommand.service.CommandServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaResponseListener {
    @Autowired
    CommandServices commandServices;
    @KafkaListener(topics = KafkaConfigUtils.COMMAND_RESPONSE, groupId = "Cmd-resp")
    void listener(String data){
        commandServices.executeReceivedCommand(data);
    }
}
