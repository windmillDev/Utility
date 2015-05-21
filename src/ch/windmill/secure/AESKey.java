package ch.windmill.secure;

import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class AESKey extends Key{
    private byte[] key;
    
    /**
     * 
     * @param key 
     */
    public AESKey(final byte[] key) {
        super("AES");
        this.key = key;
    }
    
    /**
     * 
     * @return 
     */
    public byte[] getKey() {
        return key;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return getAlgorithm()+" - "+DatatypeConverter.printBase64Binary(key);
    }
}
