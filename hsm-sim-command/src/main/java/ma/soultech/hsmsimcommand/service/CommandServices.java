package ma.soultech.hsmsimcommand.service;

import ma.soultech.hsmsimcommand.producers.KafkaResponseProducer;
import ma.soultech.hsmsimcore.commands.Command;
import ma.soultech.hsmsimcore.factory.CmdFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandServices {

    public CommandServices() {
        System.out.println("CommandServices scanned !!!! ");
    }

    @Autowired
    KafkaResponseProducer kafkaResponseProducer;
    @Autowired
    CmdFactory cmdFactory;

    public void executeReceivedCommand(String cmd){
        Command receivedCommand = cmdFactory.getCmd(cmd.substring(0,2));
        String response = receivedCommand.execute(cmd.substring(2));
        kafkaResponseProducer.sendResponse(response);
    }
}
