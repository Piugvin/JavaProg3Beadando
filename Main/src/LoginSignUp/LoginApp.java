package LoginSignUp;
import Main.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.swing.*;
public class LoginApp {
    JTextField usernameField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);
    public LoginApp() {
        JFrame login = new JFrame("Bejelentkezés");

        JLabel usernameLabel = new JLabel("Felhasználónév:");
        JLabel passwordLabel = new JLabel("Jelszó:");
        JButton loginButton = new JButton("Bejelentkezés");
        JButton backButton = new JButton("Vissza");
        login.setLayout(null);

        login.add(usernameLabel);
        login.add(usernameField);
        login.add(passwordLabel);
        login.add(passwordField);

        login.add(loginButton);
        login.add(backButton);

        login.setSize(400, 500);
        usernameLabel.setBounds(150, 50, 200, 50);
        usernameField.setBounds(100, 100, 200, 50);
        passwordLabel.setBounds(179, 150, 200, 50);
        passwordField.setBounds(100, 200, 200, 50);
        loginButton.setBounds(100, 300, 200,50);
        backButton.setBounds(100, 400, 200,50);

        login.setVisible(true);
        loginButton.addActionListener(e -> login());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.dispose();
                new Main();
            }
        });

        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
}