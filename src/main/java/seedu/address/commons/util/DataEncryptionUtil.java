package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * A class containing utility methods to encrypt and decrypt files for Storage
 */
public class DataEncryptionUtil {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static SecretKey secretKey;
    private static final String AES_KEY_FILEPATH = "data/secretkey.key";

    /**
     * Encrypts file using {@code secretKey}
     * @param file the file given to encrypt
     * @throws IOException if an input or output exception occurred
     */
    public static void encrypt(File file) throws IOException {
        loadKey();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecureRandom rnd = new SecureRandom();
            IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(16));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            processFile(cipher, file);
        } catch (GeneralSecurityException e) {
            logger.severe("Failed to encrypt message " + StringUtil.getDetails(e));
        }
    }

    /**
     * Decrypts file using {@code secretKey} located at {@code AES_KEY_FILEPATH}
     *
     * @param file the file given to decrypt
     * @throws IOException if an input or output exception occurred
     */
    public static void decrypt(File file) throws IOException {
        loadKey();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecureRandom rnd = new SecureRandom();
            IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(16));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            processFile(cipher, file);
        } catch (GeneralSecurityException e) {
            logger.severe("Failed to decrypt message " + StringUtil.getDetails(e));
        }
    }

    /**
     * Encrypts or decrypts the given file using the given cipher
     * @param cipher mode determines whether to encrypt or decrypt
     * @param file the file given to encrypt or decrypt
     */
    private static void processFile(Cipher cipher, File file) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            fis.read(inputBytes);

            FileOutputStream fos = new FileOutputStream(file);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            fos.write(outputBytes);

            fis.close();
            fos.close();

        } catch (IllegalBlockSizeException e) {
            logger.warning("File data is not a multiple of the block-size");
            encrypt(file);
        } catch (BadPaddingException e) {
            logger.warning("Key or File data is invalid " + StringUtil.getDetails(e));
        }
    }

    /**
     * Reads the key from the path given by {@code AES_KEY_FILEPATH}
     */
    private static void loadKey() {
        try {
            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(AES_KEY_FILEPATH));
            secretKey = (SecretKey) ois.readObject();
        } catch (IOException oie) {
            logger.severe("Failed to read key from file");
            createKey();
        } catch (ClassNotFoundException cnfe) {
            logger.severe("Failed to typecast to class SecretKey");
            createKey();
        }
        logger.fine("Key successfully read from file");
    }

    /**
     * Initializes and creates a key
     */
    private static void createKey() {
        initKey();
        saveKey();
    }

    /**
     * Creates a new key with the filepath {@code AES_KEY_FILEPATH}
     */
    private static void initKey() {
        KeyGenerator keyGen = null;

        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Failed to generate AES key");
        }

        keyGen.init(256);
        secretKey = keyGen.generateKey();
        logger.fine("Key generated");
    }

    /**
     * Saves the key at the filepath {@code AES_KEY_FILEPATH}
     */
    private static void saveKey() {
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(AES_KEY_FILEPATH));
            oos.writeObject(secretKey);
            oos.close();
        } catch (IOException e) {
            logger.severe("Failed to save key to file");
        }
        logger.fine("Key saved to file");
    }
}
