package ma.soultech.hsmsimtester.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class TcpClientConfig {
    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private int serverPort;

    @Bean
    public AbstractClientConnectionFactory tcpClient() {
        TcpNetClientConnectionFactory clientCf = new TcpNetClientConnectionFactory(serverIp, serverPort);
        ByteArrayLengthHeaderSerializer serializer = new ByteArrayLengthHeaderSerializer(2);
        ByteArrayLengthHeaderSerializer deSerializer = new ByteArrayLengthHeaderSerializer(2);
        clientCf.setSerializer(serializer);
        clientCf.setDeserializer(deSerializer);
        clientCf.setSoTcpNoDelay(true);
        clientCf.setSoKeepAlive(true);
        return clientCf;
    }

    @MessagingGateway(defaultRequestChannel = "toTcpChannel")
    public interface TcpClientGateway {
        byte[] send(byte[] data);
    }

    @Bean
    public MessageChannel toTcpChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "toTcpChannel")
    public TcpOutboundGateway tcpOutboundGateway() {
        System.out.println("tcpOutboundGateway scanned");
        TcpOutboundGateway outboundGateway = new TcpOutboundGateway();
        outboundGateway.setConnectionFactory(tcpClient());
        return outboundGateway;
    }

}
