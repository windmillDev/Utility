package ch.windmill.secure;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Key {
    private final String algorithm;
    
    public Key(final String algorithm) {
        this.algorithm = algorithm;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
}
