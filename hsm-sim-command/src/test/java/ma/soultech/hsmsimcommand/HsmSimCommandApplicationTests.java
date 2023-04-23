package ma.soultech.hsmsimcommand;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimcommand.config.KafkaConfigUtils;
import ma.soultech.hsmsimcommand.producers.KafkaRequestProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@SpringBootTest
class HsmSimCommandApplicationTests {
	@Autowired
	KafkaRequestProducer kafkaRequestProducer;

	@Test
	void contextLoads() {
	}

	@Test
	void reqRespTopicsTest() {
		byte[] response = null;
		byte[] request = "CWU013151701020406191B0D0F180A1C1E04260000000500014;2112206".getBytes(StandardCharsets.UTF_8);
		String key = UUID.randomUUID().toString();
		kafkaRequestProducer.sendRequest(key, request);
	}

}
