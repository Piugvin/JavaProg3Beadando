package LoginSignUp;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class LoginApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginApp() {
        setTitle("Bejelentkezés");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Felhasználónév:");
        JLabel passwordLabel = new JLabel("Jelszó:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Bejelentkezés");

        setLayout(new GridLayout(3, 2));
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);
        loginButton.addActionListener(e -> login());
    }
    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        Connection connection = null;
        PreparedStatement preparedStatement;
        final Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
            properties.load(input);
            String url = properties.getProperty("dburl");
            String username1 = properties.getProperty("username");
            String password1 = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username1, password1);
            try {
                String sql = ("SELECT users (username, password) VALUES (?,?)");

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, Arrays.toString(password));

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Sikeres Bejelentkezés !");
                } else {
                    System.out.println("Sikertelen Bejelentkezés!");
                }
            } catch (SQLException e) {
                System.out.println("Sikertelen adatbázis kapcsolat!");
                System.err.println("JDBC Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginApp().setVisible(true));
    }
}
