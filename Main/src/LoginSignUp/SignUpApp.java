package LoginSignUp;

import Snake.*;

import java.awt.*;
import java.io.FileInputStream;;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.*;
public class SignUpApp extends JFrame {
    JTextField usernameField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);
    JPasswordField CpasswordField = new JPasswordField(20);
    public  SignUpApp() {
        JFrame register = new JFrame("Regisztráció");
        //Background
        ImageIcon background = new ImageIcon(ClassLoader.getSystemResource("Retro Snake Game Background.jpg"));
        JLabel backgroundLabel = new JLabel(new ImageIcon(background.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH)));
        ImageIcon scaledBackgroundImage = new ImageIcon(background);
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, 400, 500);
        register.add(backgroundLabel);
        JLabel usernameLabel = new JLabel("Felhasználónév:");
        JLabel passwordLabel = new JLabel("Jelszó:");
        JLabel CpasswordLabel = new JLabel("Jelszó megerősítés:");

        JButton signUpButton = new JButton("Regisztráció");

        register.setLayout(null);

        register.add(usernameLabel);
        register.add(usernameField);
        register.add(passwordLabel);
        register.add(passwordField);
        register.add(CpasswordLabel);
        register.add(CpasswordField);

        register.add(signUpButton);

        register.setSize(400, 500);
        usernameLabel.setBounds(150, 50, 200, 50);
        usernameField.setBounds(100, 100, 200, 50);
        passwordLabel.setBounds(179, 150, 200, 50);
        passwordField.setBounds(100, 200, 200, 50);
        CpasswordLabel.setBounds(141, 250, 200, 50);
        CpasswordField.setBounds(100, 300, 200, 50);
        signUpButton.setBounds(100, 400, 200,50);
        register.setVisible(true);

        signUpButton.addActionListener(e -> signUp());
        register.setLocationRelativeTo(null);
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void signUp() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        char[] Cpassword = CpasswordField.getPassword();
        if(Arrays.equals(Cpassword, password)) {
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
                    String sql = ("INSERT INTO users (username, password) VALUES (?,?)");

                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, Arrays.toString(password));
                    if (Arrays.equals(password, Cpassword)) {
                        System.out.println("Regisztráció sikeres!");
                        new GameFrame();
                    } else {
                        System.out.println("Sikertelen regisztráció!");
                    }
                } catch (SQLException e) {
                    System.out.println("Sikertelen kapcsolat!");
                    System.err.println("JDBC Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            JOptionPane.showMessageDialog(null, "A jelszavak nem egyeznek.");

        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpApp().setVisible(true));
    }
}