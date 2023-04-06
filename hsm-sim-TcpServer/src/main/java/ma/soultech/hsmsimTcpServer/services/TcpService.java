package ma.soultech.hsmsimTcpServer.services;

import ma.soultech.HsmDataConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@MessageEndpoint
public class TcpService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @ServiceActivator(inputChannel = "fromTcp")
    public byte[] handleMessage(byte[] msg) {
        log.info("Received request: {}", HsmDataConverter.printCommandFromByte(msg));
        return new byte[]{msg[4],msg[5]};
    }

}
