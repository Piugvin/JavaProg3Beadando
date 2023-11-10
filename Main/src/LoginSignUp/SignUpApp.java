package LoginSignUp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Scanner;
import com.JavaBe.DBconn.*;



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

        if (password == confirmPassword) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                String url = "jdbc:mysql://localhost:3306/my_database";
                String sqlusername = "your_username";
                String sqlpassword = "your_password";

                connection = DriverManager.getConnection(url, sqlusername, sqlpassword);

                if (connection != null) {
                    System.out.println("Connected to the database!");
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
                }

            } catch (SQLException e) {
                System.out.println("Failed to connect to the database!");
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}