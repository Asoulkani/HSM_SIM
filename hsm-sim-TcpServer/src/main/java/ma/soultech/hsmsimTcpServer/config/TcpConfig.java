package ma.soultech.hsmsimTcpServer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class TcpConfig {
    @Value("${server.port}")
    private int port;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public AbstractServerConnectionFactory tcpServer() {
        log.info("Starting HSM SIM TCP server with port: {}", port);
        TcpNetServerConnectionFactory serverCf = new TcpNetServerConnectionFactory(port);
        ByteArrayLengthHeaderSerializer serializer = new ByteArrayLengthHeaderSerializer(2);
        ByteArrayLengthHeaderSerializer deSerializer = new ByteArrayLengthHeaderSerializer(2);
        serverCf.setSerializer(serializer);
        serverCf.setDeserializer(deSerializer);
        serverCf.setSoTcpNoDelay(true);
        serverCf.setSoKeepAlive(true);
        return serverCf;
    }

    @Bean
    public MessageChannel fromTcp() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel toTcp() {
        return new DirectChannel();
    }

    @Bean
    public TcpInboundGateway tcpInGate() {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(tcpServer());
        inGate.setRequestChannel(fromTcp());
        inGate.setReplyChannel(toTcp());
        return inGate;
    }
}
