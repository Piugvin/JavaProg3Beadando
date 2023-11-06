import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake extends Application {
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;
    public static final int TILE_SIZE = 20;

    private List<Coordinate> snake;
    private Coordinate food;
    private Direction currentDirection;
    private Direction nextDirection;
    private boolean isGameOver;

    public Snake() {
        snake = new ArrayList<>();
        snake.add(new Coordinate(5, 5)); // Initial snake position
        food = generateFoodPosition();
        currentDirection = Direction.RIGHT;
        nextDirection = Direction.RIGHT;
        isGameOver = false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (currentDirection != Direction.DOWN)
                        nextDirection = Direction.UP;
                    break;
                case DOWN:
                    if (currentDirection != Direction.UP)
                        nextDirection = Direction.DOWN;
                    break;
                case LEFT:
                    if (currentDirection != Direction.RIGHT)
                        nextDirection = Direction.LEFT;
                    break;
                case RIGHT:
                    if (currentDirection != Direction.LEFT)
                        nextDirection = Direction.RIGHT;
                    break;
            }
        });

        new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) { // Update every 100ms
                    if (!isGameOver) {
                        moveSnake();
                        checkCollisions();
                        drawGame(gc);
                        lastUpdate = now;
                    }
                }
            }
        }.start();

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void moveSnake() {
        if (currentDirection != nextDirection) {
            currentDirection = nextDirection;
        }

        Coordinate head = snake.get(0);
        Coordinate newHead;

        switch (currentDirection) {
            case UP:
                newHead = new Coordinate(head.getX(), head.getY() - 1);
                break;
            case DOWN:
                newHead = new Coordinate(head.getX(), head.getY() + 1);
                break;
            case LEFT:
                newHead = new Coordinate(head.getX() - 1, head.getY());
                break;
            case RIGHT:
                newHead = new Coordinate(head.getX() + 1, head.getY());
                break;
            default:
                newHead = head;
        }

        snake.add(0, newHead);

        if (newHead.equals(food)) {
            food = generateFoodPosition();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private void checkCollisions() {
        Coordinate head = snake.get(0);

        if (head.getX() < 0 || head.getX() >= WINDOW_WIDTH / TILE_SIZE ||
                head.getY() < 0 || head.getY() >= WINDOW_HEIGHT / TILE_SIZE) {
            isGameOver = true;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                isGameOver = true;
                break;
            }
        }
    }

    private Coordinate generateFoodPosition() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(WINDOW_WIDTH / TILE_SIZE);
            y = random.nextInt(WINDOW_HEIGHT / TILE_SIZE);
        } while (snake.contains(new Coordinate(x, y)));
        return new Coordinate(x, y);
    }

    private void drawGame(GraphicsContext gc) {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        gc.setFill(Color.RED);
        gc.fillRect(food.getX() * TILE_SIZE, food.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        gc.setFill(Color.GREEN);
        for (Coordinate segment : snake) {
            gc.fillRect(segment.getX() * TILE_SIZE, segment.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        if (isGameOver) {
            gc.setFill(Color.RED);
            gc.fillText("Game Over", 250, 300);
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static class Coordinate {
        private final int x;
        private final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean equals(Coordinate other) {
            return this.x == other.getX() && this.y == other.getY();
        }
    }
}
