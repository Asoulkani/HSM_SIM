package ma.soultech.hsmsimcore.cryptography;

import ma.soultech.HsmDataConverter;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Component
public class Encryption {
    public byte[] encrypt(byte[] DataToCrypt, SecretKey key, String transformation)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(DataToCrypt);
    }

    public byte[] decrypt(byte[] DataToDecrypt, SecretKey key, String transformation)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(DataToDecrypt);
    }

    public byte[] cryptTripleDES(byte[] dataToCrypt, String key) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKey keyA, keyB, keyC = null;
        byte[] result;
        if (key.length() == CryptographyUtils.DOUBLE_LENGTH_HEX) {
            keyA = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(0, 16)), CryptographyUtils.DES_ALGORITHM);
            keyB = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(16)), CryptographyUtils.DES_ALGORITHM);
        } else if (key.length() == CryptographyUtils.TRIPLE_LENGTH_HEX) {
            keyA = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(0, 16)), CryptographyUtils.DES_ALGORITHM);
            keyB = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(16, 32)), CryptographyUtils.DES_ALGORITHM);
            keyC = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(32)), CryptographyUtils.DES_ALGORITHM);
        } else
            throw new InvalidKeyException();
        result = this.encrypt(dataToCrypt, keyA, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
        result = this.decrypt(result, keyB, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
        result = this.encrypt(result, Objects.requireNonNullElse(keyC, keyA), CryptographyUtils.ENCRYPTION_TRANSFORMATION);
        return result;
    }

    public byte[] decryptTripleDes(byte[] dataToDecrypt, String key) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKey keyA, keyB, keyC = null;
        byte[] result;
        if (key.length() == CryptographyUtils.DOUBLE_LENGTH_HEX) {
            keyA = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(0, 16)), CryptographyUtils.DES_ALGORITHM);
            keyB = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(16)), CryptographyUtils.DES_ALGORITHM);
        } else if (key.length() == CryptographyUtils.TRIPLE_LENGTH_HEX) {
            keyA = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(0, 16)), CryptographyUtils.DES_ALGORITHM);
            keyB = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(16, 32)), CryptographyUtils.DES_ALGORITHM);
            keyC = new SecretKeySpec(HsmDataConverter.hexToByte(key.substring(32)), CryptographyUtils.DES_ALGORITHM);
        } else
            throw new InvalidKeyException();
        result = this.decrypt(dataToDecrypt, keyA, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
        result = this.encrypt(result, keyB, CryptographyUtils.ENCRYPTION_TRANSFORMATION);
        result = this.decrypt(result, Objects.requireNonNullElse(keyC, keyA), CryptographyUtils.ENCRYPTION_TRANSFORMATION);
        return result;
    }
}
