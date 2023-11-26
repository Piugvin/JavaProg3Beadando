package LoginSignUp;

import Main.Main;
import Snake.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
@SuppressWarnings({"InstantiationOfUtilityClass", "CallToPrintStackTrace"})
public class LoginApp {
    static JTextField usernameField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);

    public LoginApp() {
        JFrame login = new JFrame("Bejelentkezés");

        JLabel usernameLabel = new JLabel("Felhasználónév:");
        JLabel passwordLabel = new JLabel("Jelszó:");
        JButton loginButton = new JButton("Bejelentkezés");
        JButton backButton = new JButton("Mégse");
        login.setLayout(null);

        login.add(usernameLabel);
        login.add(usernameField);
        login.add(passwordLabel);
        login.add(passwordField);

        login.add(loginButton);
        login.add(backButton);

        login.setSize(400, 400);
        usernameLabel.setBounds(150, 50, 200, 50);
        usernameField.setBounds(100, 100, 200, 50);
        passwordLabel.setBounds(179, 150, 200, 50);
        passwordField.setBounds(100, 200, 200, 50);
        loginButton.setBounds(100, 250, 200, 50);
        backButton.setBounds(100, 300, 200, 50);

        login.setVisible(true);
        loginButton.addActionListener(e -> login());
        backButton.addActionListener(e -> {
            login.dispose();
            new Main();
        });

        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void login() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        Connection connection;
        PreparedStatement preparedStatement;
        final Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
            properties.load(input);
            String url = properties.getProperty("dburl");
            String username1 = properties.getProperty("username");
            String password1 = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username1, password1);
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, Arrays.toString(password));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        new Accepted();
                    } else {
                        JFrame option = new JFrame("Kérem próbálja újra!");
                        JLabel already = new JLabel("Felhasználónév vagy a jelszó hibás!");
                        JButton ok = new JButton("OK!");
                        already.setForeground(Color.RED);
                        option.setLayout(null);
                        option.add(already);
                        option.add(ok);
                        option.setSize(300, 110);
                        already.setBounds(50, 1, 300, 20);
                        ok.setBounds(125, 36, 60, 40);
                        option.setLocationRelativeTo(null);
                        option.setVisible(true);
                        option.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        ok.addActionListener(a -> option.dispose());
                    }
                } catch (SQLException e) {
                }
            } catch (SQLException e) {
            }
        } catch (SQLException | IOException e) {
        }
    }
}