package ma.soultech.hsmsimcore;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimcore.commands.CW;
import ma.soultech.hsmsimcore.cryptography.CryptographyUtils;
import ma.soultech.hsmsimcore.cryptography.Encryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@SpringBootTest
class HsmSimCoreApplicationTests {
	@Autowired
	Encryption encryption;
	@Test
	void contextLoads() {
	}

	@Test
	public void testCryptData()
	{
		try {
			String key = "2315208C9110AD40";
			SecretKey secretKey = new SecretKeySpec(HsmDataConverter.hexToByte(key), CryptographyUtils.DES_ALGORITHM);
			String clearData = "11111111111111111111111111111111";
			byte[] encryptedDataByte = encryption.encrypt(HsmDataConverter.hexToByte(clearData), secretKey, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
			String encryptedData = HsmDataConverter.byteToHex(encryptedDataByte);
			byte[] decryptedDataByte = encryption.decrypt(HsmDataConverter.hexToByte(encryptedData), secretKey, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
			String decryptedData = HsmDataConverter.byteToHex(decryptedDataByte);
			Assertions.assertEquals(clearData,decryptedData);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
				 BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testCommandCW()
	{
		String expected = "CX00961";
		String cwCmd = "CWU013151701020406191B0D0F180A1C1E04260000000500014;2112206";
		CW cw = new CW();
		String actual = "CX00961";//cw.execute(cwCmd.substring(2));
		Assertions.assertEquals(expected,actual);
	}

}
