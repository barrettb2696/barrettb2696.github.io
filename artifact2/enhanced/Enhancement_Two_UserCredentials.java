package Zoo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Brandon Barrett
 */
public class userCredentials {
    // declare private variables
    private static String userName = "none";
    private static String userPassword = "none";
    private static String hashPassword = "none";
    
    // returns username when called
    public static String getName() {
        return userName;
    }
    
    //returns password when called
    public static String getPassword() {
        return userPassword;
    }
    
    // returns hashed password when called
    public static String getHashed() {
        return hashPassword;
    }
    
    // sets name variable when called 
    public static void setName(String newName) throws NoSuchAlgorithmException {
        userName = newName;
        updateHash();
    }
    
    // sets password to be converted into a hashed password using
    // message digest five (MD5) hash
    public static void setPassword(String newPassword) throws NoSuchAlgorithmException {
        userPassword = newPassword;
        updateHash();
    }

    //update to update to sha 256
    private static void updateHash() throws NoSuchAlgorithmException {
        hashPassword = sha256Hasher();
    }

    // Method for message digest five (MD5) hash
    private static String sha256Hasher() throws NoSuchAlgorithmException {
        String original = getPassword();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(original.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}