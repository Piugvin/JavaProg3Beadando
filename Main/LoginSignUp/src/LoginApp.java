import java.util.Scanner;

public class LoginApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Login");
        System.out.println("Please enter your login credentials:");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Simulate checking login (in a real application, this would involve database authentication)
        if (isValidLogin(username, password)) {
            System.out.println("\nLogin successful. Welcome, " + username + "!");
            // Perform actions after successful login
        } else {
            System.out.println("\nInvalid credentials. Login failed.");
            // Handle failed login attempts
        }
    }

    // Simulated method to check login (Replace this with actual authentication logic)
    private static boolean isValidLogin(String username, String password) {
        // Simulated logic - replace this with actual authentication logic (e.g., database lookup)
        return username.equals("admin") && password.equals("password123");
    }
}