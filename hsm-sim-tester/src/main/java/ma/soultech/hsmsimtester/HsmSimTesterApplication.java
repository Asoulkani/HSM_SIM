package ma.soultech.hsmsimtester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HsmSimTesterApplication {
	Logger log = LoggerFactory.getLogger(HsmSimTesterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HsmSimTesterApplication.class, args);
	}
}
