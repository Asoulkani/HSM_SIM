package ma.soultech.hsmsimcommand;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"ma.soultech" })
public class HsmSimCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(HsmSimCommandApplication.class, args);
	}
	/*@Bean
	public CommandLineRunner commandLineRunner(KafkaTemplate<String,String> kafkaTemplate) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				kafkaTemplate.send("command_response", "B3001001");
				//kafka-console-consumer.sh --topic command_response --from-beginning --bootstrap-server localhost:9092
				//kafka-console-producer.sh --topic command_request --bootstrap-server localhost:9092

			}
		};
	}
	@Bean
	public CommandLineRunner commandLineRunner(CmdFactory cmdFactory, Encryption encryption) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				String cyCmd = "CYU013151701020406191B0D0F180A1C1E09614260000000500014;2112206";
				String cwCmd = "CWU013151701020406191B0D0F180A1C1E04260000000500014;2112206";
				String b2Cmd = "B20004ECHO";
				System.out.println("Application started!");
				Command cmd = null;
				try {
					cmd = cmdFactory.getCmd(cyCmd.substring(0,2));
					System.out.println(cmd.execute(cyCmd.substring(2)));
					cmd = cmdFactory.getCmd(cwCmd.substring(0,2));
					System.out.println(cmd.execute(cwCmd.substring(2)));
					cmd = cmdFactory.getCmd(b2Cmd.substring(0,2));
					System.out.println(cmd.execute(b2Cmd.substring(2)));
				} catch (NoSuchBeanDefinitionException ex) {
					System.err.println("Error: " + ex.getMessage());
				}

				System.out.println("=================== encryption test ===================");
				String key = "2315208C9110AD40";
				SecretKey secretKey = new SecretKeySpec(HsmDataConverter.hexToByte(key), CryptographyUtils.DES_ALGORITHM);
				String clearData = "11111111111111111111111111111111";
				byte[] encryptedDataByte = encryption.encrypt(HsmDataConverter.hexToByte(clearData), secretKey, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
				String encryptedData = HsmDataConverter.byteToHex(encryptedDataByte);
				System.out.println("encryptedData : " + encryptedData);
				byte[] decryptedDataByte = encryption.decrypt(HsmDataConverter.hexToByte(encryptedData), secretKey, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
				String decryptedData = HsmDataConverter.byteToHex(decryptedDataByte);
				System.out.println("decryptedData : " + decryptedData);
				byte[] tDecryptData = encryption.decrypt(HsmDataConverter.hexToByte("7A960B4D0B1419F3"), secretKey, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
				System.out.println(HsmDataConverter.byteToHex(tDecryptData));
				System.out.println("Application Ended!");
			}
		};
	}

	*/
}
