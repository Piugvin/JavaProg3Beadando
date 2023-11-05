import java.util.Scanner;

class SignUpApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Sign Up");
        System.out.println("Please enter your details:");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        // Simulate storing user details (in a real application, this would be stored securely, e.g., in a database)
        System.out.println("\nThank you for signing up!");
        System.out.println("Your details:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password); // Note: In a real application, never display passwords like this
        System.out.println("Email: " + email);

        // Here, you would typically save the user details to a database or perform necessary actions for sign-up
    }
}