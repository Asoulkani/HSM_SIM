package ma.soultech.hsmsimTcpServer.services;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimTcpServer.config.KafkaConfigUtils;
import ma.soultech.hsmsimTcpServer.producers.KafkaRequestProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;

@Service
@MessageEndpoint
public class TcpService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrentHashMap<String, CompletableFuture<byte[]>> responseFutures = new ConcurrentHashMap<>();

    @Autowired
    KafkaRequestProducer kafkaResponseProducer;

    public void handleKafkaResponse(String key, byte[] msg) {
        if(key == null)
        {
            log.warn("Key is null !");
            return;
        }
        log.info("UUID : {} | Response received : {}",key, HsmDataConverter.printCommandFromByte(msg));
        CompletableFuture<byte[]> future = responseFutures.remove(key);
        if (future != null) {
            future.complete(msg);
        } else {
            log.warn("No waiting request found for key: {}", key);
        }
    }

    @ServiceActivator(inputChannel = "fromTcp")
    public byte[] handleMessage(byte[] msg) {
        String key = UUID.randomUUID().toString();
        log.info("UUID : {} | Received request: {}", key, HsmDataConverter.printCommandFromByte(msg));
        kafkaResponseProducer.sendRequest(key, msg);
        log.info("UUID : {} | request sent to topic : {}", key, KafkaConfigUtils.COMMAND_REQUEST);

        CompletableFuture<byte[]> responseFuture = new CompletableFuture<>();
        responseFutures.put(key, responseFuture);

        try {
            byte[] response = responseFuture.get(30, TimeUnit.SECONDS);
            log.info("Sending response to client : {}", HsmDataConverter.printCommandFromByte(response));
            return response;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Error waiting for Kafka response", e);
            responseFutures.remove(key);
            return null;
        }

    }

}
