package Snake;

import LoginSignUp.LoginApp;
import LoginSignUp.SignUpApp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    static final int DELAY = 175;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6; // jelszó határozza majd meg
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    int speedMultiplier = 1;
    int applesForGameOver = 4;
    BufferedImage appleImage;
    BufferedImage headUp, headDown, headLeft, headRight;
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        try {
            headUp = ImageIO.read(new File("Main/src/Snake/headup.png"));
            headDown = ImageIO.read(new File("Main/src/Snake/headdown.png"));
            headLeft = ImageIO.read(new File("Main/src/Snake/headl.png"));
            headRight = ImageIO.read(new File("Main/src/Snake/headr.png"));
            appleImage = ImageIO.read(new File("Main/src/Snake/apple.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        startGame();
    }
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
            switch (direction) {
                case 'U':
                    g.drawImage(headUp, x[0], y[0], UNIT_SIZE, UNIT_SIZE, this);
                    break;
                case 'D':
                    g.drawImage(headDown, x[0], y[0], UNIT_SIZE, UNIT_SIZE, this);
                    break;
                case 'L':
                    g.drawImage(headLeft, x[0], y[0], UNIT_SIZE, UNIT_SIZE, this);
                    break;
                case 'R':
                    g.drawImage(headRight, x[0], y[0], UNIT_SIZE, UNIT_SIZE, this);
                    break;
            }
            g.drawImage(appleImage, appleX, appleY, UNIT_SIZE, UNIT_SIZE, this);
            for (int i = 1; i < bodyParts; i++) {
                g.setColor(new Color(45, 180, 0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Titkosítási pont: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Titkosítási pont: " + applesEaten)) / 2, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }
    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
            if (applesEaten % applesForGameOver == 0) {
                running = false;
                timer.stop();
            } else {
                speedMultiplier = speedMultiplier + 1;
                timer.setDelay(DELAY / speedMultiplier);
            }
        }
    }
    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        if (x[0] < 0) {
            running = false;
        }
        else if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        else if (y[0] < 0) {
            running = false;
        }
        else if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
            Encryption.encrypt(applesEaten, SignUpApp.epwd, SignUpApp.eusn); // Call the onGameEnd method from Snake.GamePanel.Snake class
        }

    }
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Titkosítási pont: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Titkosítási pont: " + applesEaten)) / 2, g.getFont().getSize());
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Titkosítás sikeres!", (SCREEN_WIDTH - metrics2.stringWidth("Titkosítás sikeres!")) / 2, SCREEN_HEIGHT / 2);
        try {
            Thread.sleep(2000);
            System.exit(0);
            new LoginApp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            new LoginApp();
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}