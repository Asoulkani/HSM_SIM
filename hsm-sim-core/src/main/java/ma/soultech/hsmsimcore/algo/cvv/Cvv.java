package ma.soultech.hsmsimcore.algo.cvv;

import ma.soultech.HsmDataConverter;
import ma.soultech.hsmsimcore.cryptography.CryptographyUtils;
import ma.soultech.hsmsimcore.cryptography.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class Cvv {
    @Autowired
    Encryption encryption;
    public String cvvGeneration(String pan, String expirationDate, String serviceCode, String CvkPair)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {
        SecretKey cvkA = new SecretKeySpec(HsmDataConverter.hexToByte(CvkPair.substring(0, 16)), CryptographyUtils.DES_ALGORITHM);
        StringBuilder concatFields = new StringBuilder(pan + expirationDate + serviceCode);
        concatFields.append("0".repeat(Math.max(0, 32 - concatFields.length())));
        String bloc1 = concatFields.substring(0, 16);
        String bloc2 = concatFields.substring(16, 32);
        byte[] encryptedBloc1 = encryption.encrypt(HsmDataConverter.hexToByte(bloc1), cvkA,
                CryptographyUtils.ENCRYPTION_TRANSFORMATION);
        byte[] Xor = HsmDataConverter.xor(encryptedBloc1, HsmDataConverter.hexToByte(bloc2));
        byte[] tripleDESXor = encryption.cryptTripleDES(Xor, CvkPair);
        StringBuilder decimal = new StringBuilder();
        for (int i = 0; i < HsmDataConverter.byteToHex(tripleDESXor).length(); i++) {
            if (Character.isDigit(HsmDataConverter.byteToHex(tripleDESXor).charAt(i)))
                decimal.append(HsmDataConverter.byteToHex(tripleDESXor).charAt(i));
        }
        return decimal.toString().substring(0, 3);
    }

    public boolean cvvVerification(String pan, String expirationDate, String serviceCode, String CvkPair, String cvv) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return (cvvGeneration(pan, expirationDate, serviceCode, CvkPair).equals(cvv));
    }
}
