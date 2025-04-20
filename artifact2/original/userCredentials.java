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
        userCredentials.setMD5();
    }
    // sets hashed password to be checked using a message digest five (MD5) hash
    public static void setMD5() throws NoSuchAlgorithmException {
        hashPassword = md5Hasher();
    }
    // sets password to be converted into a hashed password using
    // message digest five (MD5) hash
    public static void setPassword(String newPassword) throws NoSuchAlgorithmException {
        userPassword = newPassword;
        userCredentials.setMD5();
    }
    // Method for message digest five (MD5) hash
    private static String md5Hasher() throws NoSuchAlgorithmException {
        String original = getPassword().toString();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
