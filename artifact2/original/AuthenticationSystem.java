
package Zoo;

// Import libraries
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Brandon Barrett
 */

public class AuthenticationSystem {
    // Used ArrayLists with String Types to simplify using the get and set methods
    public static ArrayList<String> userName = new ArrayList<String>();
    public static ArrayList<String> hash = new ArrayList<String>();
    public static ArrayList<String> passWord = new ArrayList<String>();
    public static ArrayList<String> role = new ArrayList<String>();
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        readCredentialFile(); // call readCredentialFile() method
        login(); // call login() method
    }
    
    public static boolean compareCredentials() throws NoSuchAlgorithmException {
        // Compare username/password taken from userCredentials class getName method
        // to username/password entered and continue
        if (userName.contains(userCredentials.getName())) {
            int name = userName.indexOf(userCredentials.getName());
            String md5Pass = hash.get(name).toString(); 
            
            // if hashed password matches credentials file return true
            if (md5Pass.equals(userCredentials.getHashed())) {
                return true;
            }
            else {
                // if it does not display not valid to user and return false
                System.out.println("\nPassword entered is not valid");
                return false;
            }
        }
        else { // if Username does not match userCredentials display not valid
            System.out.println("\nUsername entered is not valid");
            // Give user option to exit(aka logout) after 1st failed attempt
            System.out.print("\nWould you like to exit? y or n: ");
            Scanner scnr = new Scanner(System.in);
            String response = scnr.next();
            System.out.println("");
            if (response.equals("y")) {
                System.out.println("Exiting..");
                System.exit(0);
            }
            if(response.equals("n")) {
                return false;
            }
            return false;
           
        }
    }
    
    public static void readCredentialFile() throws IOException {
        // Read content in from classpath using BufferedReader/ URL to avoid hard
        // coding path location. You just need to have resources files 
        // (aka credentials etc.) in the class file path
        URL filePath = AuthenticationSystem.class.getResource("credentials.txt");
        File file = new File(filePath.getFile());
        // Used Bufferedreader to read input in from specified file
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line = null;
            // read and split up info at tabs into arrays
            while ((line = fileReader.readLine()) != null) {
                if (line.length() > 0) {
                    String[] array = line.split("\\t");
                    // read in username credential
                    userName.add(array[0]); // add index 0 to userName arraylist
                    //read in md5 hash code
                    hash.add(array[1]); // add index 1 to hash arraylist
                    // read in password
                    // trim off quotation marks from password in file
                    // add index 2 to passWord arraylist
                    String trimmedPassword = array[2].replace("\"", "");
                    passWord.add(trimmedPassword);
                    // read in role
                    role.add(array[3]); // add index 3 to role arraylist
                    
                }
            }   
        }
    // Logout method created to call to exit program and display "Logged out"
    public static void logout() {
        System.out.println("User has Logged Out");
        System.exit(0);
    }
    // Find and Display roles and role information
    public static void displayRole() throws IOException {
        int title = userName.indexOf(userCredentials.getName());
        String roleFile = role.get(title).toString();
        
        // Get credentials file to find user role
        URL filePath = AuthenticationSystem.class.getResource("credentials.txt");
        File file = new File(filePath.getFile());
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line;
        
        // if user entered is an admin
        if (roleFile.equals("admin")) {
            // admin.txt file is a resource in the classpath
            filePath = AuthenticationSystem.class.getResource("admin.txt");
            file = new File(filePath.getFile());
            fileReader = new BufferedReader(new FileReader(file));
            line = null;
            // print admin info from admin file
            while((line = fileReader.readLine()) != null) {
                if (line.length() > 0) {
                    System.out.println(line);
                }
            }
            
        }
        // if user entered is a zookeeper
        if (roleFile.equals("zookeeper")) {
            // zookeeper.txt file is a resource in the classpath
            filePath = AuthenticationSystem.class.getResource("zookeeper.txt");
            file = new File(filePath.getFile());
            fileReader = new BufferedReader(new FileReader(file));
            line = null;
            // print zookeeper info from zookeeper file
            while ((line = fileReader.readLine()) != null) {
                if (line.length() > 0) {
                    System.out.println(line);
                }
            }
        }
        // if user entered is a veterinarian
        if (roleFile.equals("veterinarian")) {
            // veterinarian.txt file is a resource in the classpath
            filePath = AuthenticationSystem.class.getResource("veterinarian.txt");
            file = new File(filePath.getFile());
            fileReader = new BufferedReader (new FileReader(file));
            line = null;
            // print veterinarian infor from veterinarian file
            while ((line = fileReader.readLine()) != null) {
                if (line.length() > 0) {
                    System.out.println(line);
                }
            }
        }
        fileReader.close(); // Close file
    }
    public static void login() throws NoSuchAlgorithmException, IOException {
        // Ask for user name and password
        Scanner scnr = new Scanner(System.in);
        
        while (true) {
            int giveChoice = 0; // Initialize giveChoice
            
            // Display Heading and Prompts to user
            System.out.println("\nZOO AUNTHENTICATION SYSTEM");
            System.out.println("Select Option:\n");
            System.out.println("(1) Login");
            System.out.println("(2) Logout\n");
            System.out.print("Enter Option: ");
            
            // Used parseInt to convert the number option entered
            // represented as a string into an integer type.
            // Store entered response into variable giveChoice
            giveChoice = Integer.parseInt(scnr.nextLine());
            System.out.println("");

            // if user enters 1 login process begins        
            if (giveChoice == 1) {
                int attempts = 0; // intialize attempts
            
                // while user attempts is < 3
                while (attempts < 3) {
                    
                    // continue to prompt user for username/password
                    System.out.print("Username: ");
                    userCredentials.setName(scnr.nextLine().trim());
                    System.out.print("Password: ");
                    userCredentials.setPassword(scnr.nextLine().trim());
                    
                    // if user enters true credentials
                    if (compareCredentials() == true) {
                        displayRole(); //display role information for that user
                        break;
                    }
                    else {
                        // if user does not login successfully count attempts
                        attempts++;
                    }
                }
                // Do not allow user attempts to exceed 3 
                if (attempts == 3) {
                    // After 3 attempts display maximum attempts and exit
                    System.out.println("You have reached total allowed attempts: Exiting Program..");
                    System.exit(0);
                }
            } // if user enters 2 at main menu prompt logout
            else if (giveChoice == 2) {
                logout(); // invoke logout method
            }
            
        }
    }
}
