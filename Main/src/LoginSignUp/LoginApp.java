package LoginSignUp;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class LoginApp {
    public static void main(String[] args) throws SQLException {
        final Properties properties = new Properties();
        Connection connection = null;

        try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
            properties.load(input);
            String url = properties.getProperty("dburl");
            String username1 = properties.getProperty("username");
            String password1 = properties.getProperty("password");
            try {
                connection = DriverManager.getConnection(url, username1, password1);
                Statement myStatement = connection.createStatement();
                System.out.println("Sikeres kapcsolodás! ");

            } catch (SQLException e) {
                System.out.println("Sikeretelen kapcsolat!");
                System.err.println("JDBC Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Bejelentkezés!");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Kérem adja meg a felhasználónevét:");
            String username = scanner.nextLine();
            System.out.println("Kérem adja meg a jelszavát:");
            String password = scanner.nextLine();

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.setString(2, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login successful! Welcome, " + username + "!");
            } else {
                System.out.println("\nHibás felhasználónév vagy jelszó. Sikeretelen bejelentkezés.");
            }
        }
    }

