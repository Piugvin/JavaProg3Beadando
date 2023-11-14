package LoginSignUp;

import Snake.*;
import java.io.FileInputStream;;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.*;
import java.awt.*;
class SignUpApp extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField CpasswordField;
    private JButton signUpButton;
    public SignUpApp() {
        setTitle("Regisztráció");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Felhasználónév:");
        JLabel passwordLabel = new JLabel("Jelszó");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        CpasswordField = new JPasswordField(20);

        signUpButton = new JButton("Regisztráció");

        setLayout(new GridLayout(3, 2));
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(CpasswordField);
        add(new JLabel());
        add(signUpButton);

        // Set up the button action listener
        signUpButton.addActionListener(e -> signUp());
    }

    private void signUp() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        char[] Cpassword = CpasswordField.getPassword();
        if(Cpassword.equals(password)) {
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

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
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