package Snake;
import LoginSignUp.LoginApp;
import LoginSignUp.SignUpApp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
public class GameFrame extends JFrame {
    public GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        // Ablak bezárásának figyelése
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleWindowClosing();
            }
        });
    }
    private void handleWindowClosing() {
        // Bezárjuk az aktuális ablakot
        try {
            Thread.sleep(2000);
            dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Megnyitjuk a LoginApp ablakot vagy más ablakot
        new LoginApp();
    }
}