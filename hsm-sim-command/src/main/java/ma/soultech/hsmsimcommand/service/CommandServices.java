package ma.soultech.hsmsimcommand.service;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimcommand.producers.KafkaResponseProducer;
import ma.soultech.hsmsimcore.commands.Command;
import ma.soultech.hsmsimcore.factory.CmdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class CommandServices {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommandServices() {
        System.out.println("CommandServices scanned !!!! ");
    }

    @Autowired
    KafkaResponseProducer kafkaResponseProducer;
    @Autowired
    CmdFactory cmdFactory;

    public void executeReceivedCommand(String key, byte[] cmd){
        String command = HsmDataConverter.printCommandFromByte(cmd);
        logger.info("Received Command : {}" ,command);
        Command receivedCommand = cmdFactory.getCmd(command.substring(0,2));
        logger.info("Command : {}", receivedCommand.getDescription());
        byte[] response = receivedCommand.execute(command.substring(2)).getBytes(StandardCharsets.UTF_8);
        logger.info("Responce : {}", HsmDataConverter.printCommandFromByte(response));
        kafkaResponseProducer.sendResponse(key, response);
    }
}
