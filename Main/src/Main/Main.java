package Main;
import javax.swing.*;
import java.awt.*;
import LoginSignUp.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JButton signup = new JButton("Regisztráció");
        JButton login = new JButton("Bejelentkezés");

        frame.setLayout(new FlowLayout());

        signup.setSize(200, 50);
        login.setSize(200, 50);

        frame.add(signup);
        frame.add(login);

        frame.setSize(300, 100);
        frame.setVisible(true);

        signup.addActionListener(e -> new SignUpApp());
        login.addActionListener(e -> new LoginApp());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
