package LoginSignUp;

import Main.Main;
import Snake.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.*;
import java.net.URL;
public class SignUpApp {
    public static String usn;
    JTextField usernameField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);
    JPasswordField CpasswordField = new JPasswordField(20);
    public  SignUpApp() {
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
        signUpButton.setBounds(100, 400, 200,50);
        backButton.setBounds(100, 500, 200,50);
        register.setVisible(true);

        signUpButton.addActionListener(e -> signUp());
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register.dispose();
                new Main();
            }
        });
        register.setLocationRelativeTo(null);
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static String epwd="", eusn="";
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
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected > 0) {
                    if (Arrays.equals(password, Cpassword)) {
                        System.out.println("Regisztráció sikeres!");
                         new GameFrame();
                         eusn = username;
                         epwd= Arrays.toString(password);
                    } else {
                        System.out.println("Sikertelen regisztráció!");
                    }
                    }
            }
                catch (SQLException e) {
                    System.out.println("Sikertelen kapcsolat!");
                    System.err.println("JDBC Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);}
    }
    }
}
