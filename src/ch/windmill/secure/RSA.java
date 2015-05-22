package ch.windmill.secure;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Provides functions to encode and decode with the RSA algorithm. This class needs RSA keys. See
 * <code>ch.windmill.secure.RSAKeyPair</code> and <code>ch.windmill.secure.RSAKey</code>.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class RSA {
    private final RSAKey pubKey;
    private final RSAKey privKey;
    
    /**
     * Creates a new RSA object.
     * @param pubKey The public key for encoding.
     * @param privKey The private key for decoding.
     */
    public RSA(final RSAKey pubKey, final RSAKey privKey) {
        this.pubKey = pubKey;
        this.privKey = privKey;
    }
    
    /**
     * Create parts with the max length of keyLenght. If the string is shorter than keyLength, the arrayList
     * has only one value with the whole string s.
     * @param s The string to check.
     * @param keyLength The max length of one part.
     * @return Separated List with all parts.
     */
    private ArrayList<String> toBalancedBlocks(final String s, final int blockLength) {
         ArrayList<String> blocks = new ArrayList<>();
         
         if(s.length() > blockLength) {
             String block = "";
             for(int i = 0; i < s.length(); i++) {
                 block += s.charAt(i);
                 if(block.length() == blockLength) {
                     blocks.add(block);
                     System.out.println("new block "+block);
                     block = "";
                 }
             }
             
             if(block.length() > 0) {
                blocks.add(block);
                System.out.println("new block "+block);
             }
         } else {
             blocks.add(s);
         }
         
         return blocks;
    }
    
    /**
     * Encode the data with the public key.
     * @param b The data to encode.
     * @return Encoded data.
     */
    public String encode(final byte[] b) {
        String encoded = "";
        ArrayList<String> blocks = toBalancedBlocks((new BigInteger(b)).toString(), pubKey.getModulLength());
        
        for(String block : blocks) {
            encoded += (new BigInteger(block)).modPow(new BigInteger(pubKey.getExponent()),
                    new BigInteger(pubKey.getModulus())).toString();
        }
        
        return encoded;
    }
    
    /**
     * Decode the encoded string with the private key.
     * @param b The encoded data.
     * @return Decoded bytes.
     */
    public byte[] decode(final String b) {
        String decoded = "";
        ArrayList<String> blocks = toBalancedBlocks(b, privKey.getModulLength());
        
        for(String block : blocks) {
            decoded += (new BigInteger(block)).modPow(new BigInteger(privKey.getExponent()),
                    new BigInteger(privKey.getModulus())).toString();
        }
        
        return (new BigInteger(decoded)).toByteArray();
    }
}
