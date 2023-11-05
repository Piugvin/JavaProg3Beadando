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

        if (isValidLogin(username, password)) {
            System.out.println("\nLogin successful. Welcome, " + username + "!");

        } else {
            System.out.println("\nInvalid credentials. Login failed.");

        }
    }


    private static boolean isValidLogin(String username, String password) {

        return username.equals("admin") && password.equals("password123");
    }
}