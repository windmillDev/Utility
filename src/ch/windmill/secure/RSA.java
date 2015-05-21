package ch.windmill.secure;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class RSA {
    private final RSAKey pubKey;
    private final RSAKey privKey;
    
    public static void main(String[] args) {
        RSAKeyPair kp = new RSAKeyPair(512);
        //System.out.println(kp.getPublicKey().toString());
        //System.out.println(kp.getPrivateKey().toString());
        
        //RSAKey pubKey = new RSAKey(true, 100, "1127", "1210537");
        //RSAKey privKey = new RSAKey(true, 100, "438403", "1210537");
        
        RSA rsa = new RSA(kp.getPublicKey(), kp.getPrivateKey());
        //RSA rsa = new RSA(pubKey, privKey);
        String enc = rsa.encode("TEST");
        String dec = rsa.decode(enc);
        System.out.println("Encoded: "+enc);
        System.out.println("Decoded: "+dec);
        //System.out.println("TEST euclidian algo: "+kp.extendedEuclidianAlgorithm(new BigInteger("1127"), new BigInteger("1208020")));
        //System.out.println((new BigInteger("1127").modPow(BigInteger.ZERO, BigInteger.ONE));
    }
    
    public RSA(final RSAKey pubKey, final RSAKey privKey) {
        this.pubKey = pubKey;
        this.privKey = privKey;
    }
    
    private String toUnicode(final String s) {
        String unicode = "";
        for(char c : s.toCharArray()) {
            if(c < 100) {
                unicode += "0"+(int)c;
            } else {
                unicode += (int)c;
            }
        }
        
        return unicode;
    }
    
    private ArrayList<String> toBalancedBlocks(final String s, final int keyLength) {
         ArrayList<String> blocks = new ArrayList<>();
         
         if(s.length() > keyLength) {
             String block = "";
             for(int i = 0; i < s.length(); i++) {
                 if(block.length() == keyLength) {
                     blocks.add(block);
                     block = "";
                 }
             }
             
             if(block.length() > 0) {
                blocks.add(block); 
             }
         } else {
             blocks.add(s);
         }
         
         return blocks;
    }
    
    private String unicodeToString(final String s) {
        String res = "";
        for(int i = 3; i <= s.length(); i+=3) {
            res += (char) Integer.parseInt(s.substring(i-3, i));
        }
        
        return res;
    }
    
    public String encode(final String data) {
        String encoded = "";
        ArrayList<String> blocks = toBalancedBlocks(toUnicode(data), pubKey.getModulLength());
        
        for(String block : blocks) {
            if(block.charAt(0) < 100) {
                block = "999" + block;
            }
            
            encoded += (new BigInteger(block)).modPow(new BigInteger(pubKey.getExponent()),
                    new BigInteger(pubKey.getModulus())).toString();
        }
        
        return encoded;
    }
    
    public String decode(final String encoded) {
        String decoded = "";
        ArrayList<String> blocks = toBalancedBlocks(encoded, privKey.getModulLength());
        
        for(String block : blocks) {
            decoded += (new BigInteger(block)).modPow(new BigInteger(privKey.getExponent()),
                    new BigInteger(privKey.getModulus())).toString();
        }
        if(decoded.substring(0, 3).equals("999")) {
            decoded = decoded.substring(3, decoded.length());
        }
        
        return unicodeToString(decoded);
    }
}
