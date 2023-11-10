package LoginSignUp;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        int err = 0;
        if (password == confirmPassword) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            final Properties properties = new Properties();

            try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
                properties.load(input);
                String url = properties.getProperty("dburl");
                String username1 = properties.getProperty("username");
                String password1 = properties.getProperty("password");
                connection = DriverManager.getConnection(url, username1, password1);
                System.out.println("Sikeres kapcsolodás! ");
            } catch (SQLException e) {
                System.out.println("Sikeretelen kapcsolat!");
                System.err.println("JDBC Error: " + e.getMessage());
                e.printStackTrace();
                err = 1;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (err == 0) {
                try {
                    String sql = MessageFormat.format("INSERT INTO users (username, password) VALUES (''{0}'', ''{1}''", username, password);

                    preparedStatement = connection.prepareStatement(sql);
                    ((PreparedStatement) preparedStatement).setString(1, username);
                    preparedStatement.setString(2, password);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("New user added successfully!");
                    } else {
                        System.out.println("Failed to add the new user.");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}