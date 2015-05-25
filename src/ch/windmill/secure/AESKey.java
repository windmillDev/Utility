package ch.windmill.secure;

import javax.xml.bind.DatatypeConverter;

/**
 * Provides an AES key. There are no restrictions for the key length.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class AESKey extends Key{
    private byte[] key;
    
    /**
     * Creates a new AES key object. The algorithm name in the <code>super()</code> constructor is 
     * 'AES'.
     * @param key 
     */
    public AESKey(final byte[] key) {
        super("AES");
        this.key = key;
    }
    
    /**
     * Get the key value.
     * @return The key value.
     */
    public byte[] getKey() {
        return key;
    }
    
    /**
     * Write the informations of the object into a string.
     * @return String with informations about the object.
     */
    @Override
    public String toString() {
        return getAlgorithm()+" - "+DatatypeConverter.printBase64Binary(key);
    }
}
