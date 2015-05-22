package ch.windmill.secure;

import java.math.BigInteger;
import java.util.Random;

/**
 * Provides a RSA key pair. This class has methods to calculate RSA keys. A key pair includes a public
 * and a private key. A key contains a modulus <code>n</code> (the same value for both keys) and a 
 * unique exponent.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class RSAKeyPair {
    private final int keyLength;
    private BigInteger e,n,d;
    
    /**
     * Creates a RASKeyPair object. Calculates public and private key.
     * @param keyLength The length in bit of the key.
     */
    public RSAKeyPair(final int keyLength) {
        this.keyLength = keyLength;
        keyGen();
    }
    
    /**
     * Get the length of the keys in bits.
     * @return The length of the keys.
     */
    public int getKeyLength() {
        return keyLength;
    }
    
    /**
     * 
     * @return 
     */
    public RSAKey getPublicKey() {
        return new RSAKey(true, e.toString(), n.toString());
    }
    
    /**
     * 
     * @return 
     */
    public RSAKey getPrivateKey() {
        return new RSAKey(false, d.toString(), n.toString());
    }
    
    /**
     * Calculates public and private key. Set values to the fields e, n and d.
     */
    private void keyGen() {
        BigInteger totientOfN;
        Random ran = new Random();
        BigInteger p = randomPrim((keyLength /2), ran);      // two probable prime numbers 
        BigInteger q = randomPrim((keyLength /2), ran);
        
        n = p.multiply(q);                                  // calculate the modulus n
        e = randomPrim(16, ran);                            // must be comprime to the totient of n -> easiest way generate a prime number
        totientOfN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        d = extendedEuclideanAlgorithm(e, totientOfN);      // calcutae the private key d
    }
    
    /**
     * Generate a new random probable prim number with the given length.
     * @param lenght The length in bit.
     * @param r The <code>Random</code> object.
     * @return A probable random prim.
     */
    private BigInteger randomPrim(final int length, final Random r) {
        return BigInteger.probablePrime(length, r);
    }
    
    /**
     * Calculate the multiplicative inverse number. The private key value will be calculated with this method.
     * The calculation is <code>(d*factor)mod(modulus)</code>, d is the result of the calculation.
     * Negative numbers are not permitted.
     * @param factor The factor for multiplication.
     * @param modulus The modulus value.
     * @return The multiplicative inverse number.
     */
    public BigInteger extendedEuclideanAlgorithm(final BigInteger factor, final BigInteger modulus) {
        BigInteger left = factor;
        BigInteger right = modulus;
        BigInteger remainder, result = null;
        BigInteger[] vecLeft = {BigInteger.ONE, BigInteger.ZERO};
        BigInteger[] vecRight = {BigInteger.ZERO, BigInteger.ONE};
        
        do {
            if(left.compareTo(BigInteger.ONE) == 0) {           // equals than
                if(right.compareTo(BigInteger.ZERO) == 0) {
                    result = vecLeft[0];
                    break;
                }
            } else if(left.compareTo(BigInteger.ZERO) == 0) {
                if(right.compareTo(BigInteger.ONE) == 0) {
                    result = vecRight[0];
                    break;
                }
            }
            if(left.compareTo(right) == 1) {                    // greater than
                remainder = left.divide(right);
                left = left.mod(right);
                vecLeft[0] = vecLeft[0].subtract(vecRight[0].multiply(remainder));
                vecLeft[1] = vecLeft[1].subtract(vecRight[1].multiply(remainder));
            } else {
                remainder = right.divide(left);
                right = right.mod(left);
                vecRight[0] = vecRight[0].subtract(vecLeft[0].multiply(remainder));
                vecRight[1] = vecRight[1].subtract(vecLeft[1].multiply(remainder));
            }
            
        } while(result == null);
        
        if(result.signum() == -1) {                             // if the result is negative, calc a positive number
            result = result.mod(modulus);
        }
        
        return result;
    }
}
