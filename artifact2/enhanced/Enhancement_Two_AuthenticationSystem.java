
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
    public enum UserRole { ADMIN, ZOOKEEPER, VETERINARIAN }

    public static class User {
        String hashedPassword;
        UserRole role;

        public User(String hashedPassword, UserRole role) {
            this.hashedPassword = hashedPassword;
            this.role = role;
        }
    }

    // Efficient HashMap to replace multiple ArrayLists
    public static Map<String, User> credentialsMap = new HashMap<>();

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        readCredentialFile();
        login();
    }

    public static void readCredentialFile() throws IOException {
        URL filePath = AuthenticationSystem.class.getResource("credentials.txt");
        File file = new File(filePath.getFile());
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = fileReader.readLine()) != null) {
            if (line.length() > 0) {
                String[] array = line.split("\\t");
                String username = array[0];
                String hash = array[1];
                String roleStr = array[3].trim().toUpperCase();

                UserRole role = UserRole.valueOf(roleStr); // convert string to enum
                credentialsMap.put(username, new User(hash, role));
            }
        }
        fileReader.close();
    }

    public static boolean compareCredentials() throws NoSuchAlgorithmException {
        String username = userCredentials.getName();
        String hashed = userCredentials.getHashed();

        if (credentialsMap.containsKey(username)) {
            User user = credentialsMap.get(username);
            if (user.hashedPassword.equals(hashed)) {
                return true;
            } else {
                System.out.println("\nPassword entered is not valid.");
                return false;
            }
        } else {
            System.out.println("\nUsername entered is not valid.");
            System.out.print("Would you like to exit? (y/n): ");
            Scanner scnr = new Scanner(System.in);
            String response = scnr.next();
            if (response.equalsIgnoreCase("y")) {
                System.out.println("Exiting...");
                System.exit(0);
            }
            return false;
        }
    }

    public static void login() throws NoSuchAlgorithmException, IOException {
        Scanner scnr = new Scanner(System.in);

        while (true) {
            System.out.println("\nZOO AUTHENTICATION SYSTEM");
            System.out.println("Select Option:\n1. Login\n2. Exit");
            System.out.print("Enter Option: ");
            int choice = Integer.parseInt(scnr.nextLine());
            System.out.println();

            if (choice == 1) {
                int attempts = 0;
                while (attempts < 3) {
                    System.out.print("Username: ");
                    userCredentials.setName(scnr.nextLine().trim());
                    System.out.print("Password: ");
                    userCredentials.setPassword(scnr.nextLine().trim());

                    if (compareCredentials()) {
                        User user = credentialsMap.get(userCredentials.getName());
                        displayRole(user.role);
                        mainMenu(user.role);
                        break;
                    } else {
                        attempts++;
                    }
                }

                if (attempts == 3) {
                    System.out.println("You have reached the maximum allowed attempts. Exiting program...");
                    System.exit(0);
                }
            } else if (choice == 2) {
                logout();
            }
        }
    }

    public static void displayRole(UserRole role) throws IOException {
        String fileName = role.toString().toLowerCase() + ".txt";
        URL filePath = AuthenticationSystem.class.getResource(fileName);
        if (filePath == null) {
            System.out.println("Role information file not found.");
            return;
        }

        File file = new File(filePath.getFile());
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line;

        System.out.println("\n--- Role Information ---");
        while ((line = fileReader.readLine()) != null) {
            System.out.println(line);
        }
        fileReader.close();
    }

    // NEW MONITORING MENU SYSTEM BASED ON USER ROLE
    public static void mainMenu(UserRole role) {
        Scanner scnr = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Monitoring Menu ---");
            switch (role) {
                case ADMIN:
                    System.out.println("1. Monitor Animals");
                    System.out.println("2. Monitor Habitats");
                    System.out.println("3. Exit");
                    break;
                case ZOOKEEPER:
                    System.out.println("1. Monitor Animals");
                    System.out.println("2. Exit");
                    break;
                case VETERINARIAN:
                    System.out.println("1. Monitor Habitats");
                    System.out.println("2. Exit");
                    break;
            }

            System.out.print("Select an option: ");
            int choice = Integer.parseInt(scnr.nextLine());

            if ((role == UserRole.ADMIN && choice == 3) ||
                (role != UserRole.ADMIN && choice == 2)) {
                logout();
            } else {
                if (choice == 1) {
                    System.out.println("[INFO] Monitoring Animals...");
                } else if (choice == 2 && role == UserRole.ADMIN) {
                    System.out.println("[INFO] Monitoring Habitats...");
                } else if (choice == 1 && role == UserRole.VETERINARIAN) {
                    System.out.println("[INFO] Monitoring Habitats...");
                } else {
                    System.out.println("[ERROR] Invalid Option.");
                }
            }
        }
    }

    public static void logout() {
        System.out.println("User has logged out.");
        System.exit(0);
    }
}