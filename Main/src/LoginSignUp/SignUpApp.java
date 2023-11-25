package LoginSignUp;

import Main.Main;
import Snake.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.*;
import java.net.URL;
@SuppressWarnings({"InstantiationOfUtilityClass", "CallToPrintStackTrace"})
public class SignUpApp {
    JTextField usernameField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);
    JPasswordField CpasswordField = new JPasswordField(20);

    public SignUpApp() {
        JFrame register = new JFrame("Regisztráció");
        //Background
        String absolutePath = "/Main/src/LoginSignUp/background.jpg";
        try {
            URL backgroundUrl = new File(absolutePath).toURI().toURL();
            ImageIcon background = new ImageIcon(backgroundUrl);
            Image backgroundImg = background.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
            ImageIcon scaledBackgroundImage = new ImageIcon(backgroundImg);
            JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
            backgroundLabel.setBounds(0, 0, 400, 500);
            register.add(backgroundLabel);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //background
        JLabel usernameLabel = new JLabel("Felhasználónév:");
        JLabel passwordLabel = new JLabel("Jelszó:");
        JLabel CpasswordLabel = new JLabel("Jelszó megerősítés:");

        JButton signUpButton = new JButton("Regisztráció");
        JButton backButton = new JButton("Vissza");

        register.setLayout(null);
        register.add(usernameLabel);
        register.add(usernameField);
        register.add(passwordLabel);
        register.add(passwordField);
        register.add(CpasswordLabel);
        register.add(CpasswordField);

        register.add(signUpButton);
        register.add(backButton);

        register.setSize(400, 600);

        usernameLabel.setBounds(150, 50, 200, 50);
        usernameField.setBounds(100, 100, 200, 50);
        passwordLabel.setBounds(179, 150, 200, 50);
        passwordField.setBounds(100, 200, 200, 50);
        CpasswordLabel.setBounds(141, 250, 200, 50);
        CpasswordField.setBounds(100, 300, 200, 50);
        signUpButton.setBounds(100, 400, 200, 50);
        backButton.setBounds(100, 500, 200, 50);
        register.setVisible(true);
        signUpButton.addActionListener(e -> signUp());
        backButton.addActionListener(e -> {
            register.dispose();
            new Main();
        });
        register.setLocationRelativeTo(null);
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static String epwd = "", eusn = "";
    public class used {
        public void showusrname() {
            JFrame option = new JFrame("Már foglalt!");
            JLabel already = new JLabel("Felhasználónév jelenleg foglalt!");
            JButton ok = new JButton("OK!");
            already.setForeground(Color.RED);
            option.setLayout(null);
            option.add(already);
            option.add(ok);
            option.setSize(300, 100);
            already.setBounds(50, 1, 200, 20);
            ok.setBounds(125, 36,30,20);
            option.setLocationRelativeTo(null);
            option.setVisible(true);
            option.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ok.addActionListener(e -> option.dispose());
        }
    }
    private SignUpApp signUp() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();
        char[] Cpassword = CpasswordField.getPassword();
        if (Arrays.equals(Cpassword, password)) {
            Connection connection;
            PreparedStatement preparedStatement;
            final Properties properties = new Properties();
            try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
                properties.load(input);
                String url = properties.getProperty("dburl");
                String username1 = properties.getProperty("username");
                String password1 = properties.getProperty("password");
                connection = DriverManager.getConnection(url, username1, password1);
                String query = "SELECT username FROM users WHERE username = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                        SwingUtilities.invokeLater(() -> {
                            used used = new used();
                            used.showusrname();
                        });
                } else {
                    try {
                        String sql = ("INSERT INTO users (username, password) VALUES (?,?)");
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, username);
                        preparedStatement.setString(2, Arrays.toString(password));
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            new GameFrame();
                            eusn = username;
                            epwd = Arrays.toString(password);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
            }
        }
        return null;
    }
}