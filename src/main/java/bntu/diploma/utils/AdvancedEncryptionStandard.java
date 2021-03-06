package bntu.diploma.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AdvancedEncryptionStandard {

    private static final String ALGORITHM = "AES";

    public static String thisClientEncryptionKey = ApplicationProperties.prop.getProperty("encryption_key");

    /**
     * Encrypts the given plain text
     *
     * @param plainText The plain text to encrypt
     * @param key       is the key for encrypting which is
     *                  being owned by both the client and the server
     */
    public static byte[] encrypt(byte[] plainText, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainText);
    }


    /**
     * Decrypts the given byte array
     *
     * @param cipherText The data to decrypt
     */
    public static String decrypt(byte[] cipherText, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return new String(cipher.doFinal(cipherText));
    }

    public static byte[] encrypt(byte[] plainText) throws Exception {

        return encrypt(plainText, thisClientEncryptionKey.getBytes());
    }


    public static String decrypt(byte[] cipherText) throws Exception {

        return decrypt(cipherText, thisClientEncryptionKey.getBytes());
    }
}
