package LoginSignUp;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Accepted {
    public Accepted() {
        JFrame acceptedFrame = new JFrame("Adatok");
        JLabel normalLabel = new JLabel("Visszafejtett jelszó:");
        JLabel encryptLabel = new JLabel("Titkosított jelszó:");
        JLabel jelszavak = new JLabel();
        JButton button = new JButton("Jelszavak lekérése!");
        String username = LoginApp.usernameField.getText();
        final Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("Main/src/Database Configuration/DB.properties")) {
            properties.load(input);
            String url = properties.getProperty("dburl");
            String username1 = properties.getProperty("username");
            String password1 = properties.getProperty("password");
            try (Connection connection = DriverManager.getConnection(url, username1, password1)) {
                String query = "SELECT password, Encrypted_password FROM users WHERE username = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String rowData = resultSet.getString("password") + "\t" + resultSet.getString("Encrypted_password");
                            jelszavak.setText(rowData);
                        }
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        acceptedFrame.setLayout(null);
        acceptedFrame.add(normalLabel);
        acceptedFrame.add(encryptLabel);
        acceptedFrame.add(jelszavak);
        acceptedFrame.add(button);
        acceptedFrame.setSize(350, 350);
        acceptedFrame.setLocationRelativeTo(null);
        normalLabel.setBounds(10, 50, 200, 20);
        encryptLabel.setBounds(150, 50, 200, 20);
        jelszavak.setBounds(50, 75, 300, 20);
        button.setBounds(75, 150, 200, 50);
        acceptedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        acceptedFrame.setVisible(true);
        button.addActionListener(a -> {
            String filePath = "out/production/Main/LoginSignUp/file.txt";
            try {
                FileWriter fileWriter = new FileWriter(filePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(jelszavak.getText());
                bufferedWriter.close();
                System.out.println("Data has been exported to the file successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}