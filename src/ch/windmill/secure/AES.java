package ch.windmill.secure;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * This class provides AES 256 bit methods for encryption and decryption. It uses java cryptographic classes.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class AES {
    public final static int KEYSIZE = 256;
    
    private byte[] key;
    
    public static void main(String[] args) {
        AES a = new AES();
        System.out.println(a.keyToString());
        try {
            byte[] enc = a.encode("TEST".getBytes());
            System.out.println("Decrypted: "+new String(a.decode(enc)));
        } catch (Exception ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates a new AES object. Invoke the key generator method to generate a new AES key.
     */
    public AES() {
        try {
            generateKey();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Generates a new AES key with the lenght of <code>KEYSIZE<code>.
     * @throws NoSuchAlgorithmException The keygen algorithm is not supported.
     */
    private void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEYSIZE);
        this.key = keyGen.generateKey().getEncoded();
    }
    
    /**
     * Get the AES key as a formatted String.
     * @return The AES key as a String.
     */
    public String keyToString() {
        return DatatypeConverter.printBase64Binary(key);
    }
    
    /**
     * Encrypt the data.
     * @param data The data to encode.
     * @return The encoded data.
     * @throws Exception The encoding was not successfully.
     */
    public byte[] encode(final byte[] data) throws Exception {
        byte[] encoded = null;
        
        try {
            SecretKeySpec spec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, spec);         // initialize this cipher with a key
            encoded = cipher.doFinal(data);                 // encrypt data
        } catch(Exception e) {
            throw new Exception("The encoding was not successfully: "+e.getMessage());
        }
        
        return encoded;
    }
    
    /**
     * Decrypt the data.
     * @param data The data to decode.
     * @return The decoded data.
     * @throws Exception The decoding was not successfully.
     */
    public byte[] decode(final byte[] data) throws Exception {
        byte[] decoded = null;
        
        try {
            SecretKeySpec spec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, spec);         // initialize this cipher with a key
            decoded = cipher.doFinal(data);                 // decrypt data
        } catch(Exception e) {
            throw new Exception("The decoding was not successfully: "+e.getMessage());
        }
        
        return decoded;
    }
}
