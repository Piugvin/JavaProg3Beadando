package LoginSignUp;

import java.sql.*;
import java.util.Scanner;

public class LoginApp {
    public static void main(String[] args) {
        System.out.println("Bejelentkezés!");
        try {
            //Adatbázis csatlakozás
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Kérem adja meg a felhasználónevét:");
            String username = scanner.nextLine();
            System.out.println("Kérem adja meg a jelszavát:");
            String password = scanner.nextLine();

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login successful! Welcome, " + username + "!");
            } else {
                System.out.println("\nHibás felhasználónév vagy jelszó. Sikeretelen bejelentkezés.");
            }
            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}