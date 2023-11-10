package LoginSignUp;
import java.io.FileInputStream;;
import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Scanner;

class SignUpApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Üdvözöljük!");
        System.out.println("Kérem adja meg a kért adatokat.");

        System.out.print("Felhasználónév: ");
        String username = scanner.nextLine();

        System.out.print("Jelszó: ");
        String password = scanner.nextLine();

        System.out.print("Jelszó megerősítése: ");
        String confirmPassword = scanner.nextLine();
        if (password.equals(confirmPassword)) {
            Connection connection = null;
            PreparedStatement preparedStatement;
            final Properties properties = new Properties();
            try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
                properties.load(input);
                String url = properties.getProperty("dburl");
                String username1 = properties.getProperty("username");
                String password1 = properties.getProperty("password");
                connection = DriverManager.getConnection(url, username1, password1);
                System.out.println("Sikeres kapcsolodás! ");
                try {
                    String sql =("INSERT INTO users (username, password) VALUES (?,?)");

                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("New user added successfully!");
                    } else {
                        System.out.println("Failed to add the new user.");
                    }
                } catch (SQLException e) {
                System.out.println("Sikeretelen kapcsolat!");
                System.err.println("JDBC Error: " + e.getMessage());
                e.printStackTrace();
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
