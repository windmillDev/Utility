package ch.windmill.secure;

/**
 * This class represents a public or private RSA key.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class RSAKey extends Key {
    private boolean isPublic;
    private String exp;
    private String modulus;
    private int modulLength;
    
    /**
     * Creates a new RSAKey object.
     * @param isPublic True, if this is a public key otherwise false.
     * @param exp The exponent of the key.
     * @param modulus The modul of the key.
     */
    public RSAKey(final boolean isPublic, final String exp, final String modulus) {
        super("RSA");
        this.isPublic = isPublic;
        this.exp = exp;
        this.modulus = modulus;
        modulLength = modulus.length();
    }
    
    /**
     * 
     * @return If this is a public key or not (=private key).
     */
    public boolean isPublic() {
        return isPublic;
    }
    
    /**
     * 
     * @return The key value.
     */
    public String getExponent() {
        return exp;
    }
    
    /**
     * 
     * @return The modulus value.
     */
    public String getModulus() {
        return modulus;
    }
    
    /**
     * 
     * @return The lenght of the modul.
     */
    public int getModulLength() {
        return modulLength;
    }
    
    /**
     * Set this key public (=true) or private (=false).
     * @param isPublic Flag for the key type.
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    /**
     * Set the exponent value for this key.
     * @param exp The key value.
     */
    public void setExponent(String exp) {
        this.exp = exp;
    }
    
    /**
     * Set the modulus value for this key.
     * @param modulus The modulus value.
     */
    public void setModulus(String modulus) {
        this.modulus = modulus;
        modulLength = modulus.length();
    }
    
    /**
     * Write the algorithm, key-type and the exponent and modulus value to a String.
     * @return String contains informations about the object.
     */
    @Override
    public String toString() {
        String type = "PRIVATE";
        
        if(isPublic) {
            type = "PUBLIC";
        }
        
        return type+" "+getAlgorithm()+" KEY\nExponent: "+exp+"\nModul: "+modulus;
    }
}
