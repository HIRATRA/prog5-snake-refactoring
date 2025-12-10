package snake.model;

import java.io.IOException;
import java.util.Random;

class Game {
    // Constantes du jeu
    private static final int SCREEN_HEIGHT = 20;
    private static final int SCREEN_WIDTH = 40;
    private static final int TICK_DELAY_MS = 120;

    // Caractères d'affichage
    private static final char SNAKE_CHAR = '#';
    private static final char FOOD_CHAR = '*';
    private static final char WALL_CHAR = 'X';
    private static final char EMPTY_CHAR = ' ';

    // Séquences d'échappement terminal
    private static final String CLEAR_SCREEN = "\033[H\033[2J";

    private final Snake snake;
    private Position food;
    private int score;
    private final Random random;
    private boolean gameOver;

    public Game() {
        Position startPosition = new Position(SCREEN_HEIGHT / 2, SCREEN_WIDTH / 2);
        this.snake = new Snake(startPosition, Direction.RIGHT);
        this.random = new Random();
        this.score = 0;
        this.gameOver = false;
        this.food = generateFood();
    }

    public void start() throws IOException, InterruptedException {
        while (!gameOver) {
            handleInput();
            update();
            render();
            Thread.sleep(TICK_DELAY_MS);
        }

        displayGameOver();
    }

    private void handleInput() throws IOException {
        if (System.in.available() > 0) {
            char input = (char) System.in.read();
            Direction newDirection = mapInputToDirection(input);

            if (newDirection != null) {
                snake.setDirection(newDirection);
            }
        }
    }

    private Direction mapInputToDirection(char input) {
        switch (input) {
            case 'a': return Direction.LEFT;
            case 'd': return Direction.RIGHT;
            case 'w': return Direction.UP;
            case 's': return Direction.DOWN;
            default: return null;
        }
    }

    private void update() {
        Position nextHead = snake.getHead().move(snake.getCurrentDirection());

        // Vérifier les collisions avec les murs
        if (isOutOfBounds(nextHead)) {
            gameOver = true;
            return;
        }

        // Vérifier les collisions avec le corps
        if (snake.collidesWith(nextHead)) {
            gameOver = true;
            return;
        }

        // Vérifier si le serpent mange la nourriture
        boolean foodEaten = nextHead.equals(food);
        if (foodEaten) {
            score++;
            food = generateFood();
        }

        snake.move(foodEaten);
    }

    private boolean isOutOfBounds(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row <= 0 || row >= SCREEN_HEIGHT - 1 ||
                col <= 0 || col >= SCREEN_WIDTH - 1;
    }

    private Position generateFood() {
        Position newFood;
        do {
            int row = random.nextInt(SCREEN_HEIGHT - 2) + 1;
            int col = random.nextInt(SCREEN_WIDTH - 2) + 1;
            newFood = new Position(row, col);
        } while (snake.collidesWith(newFood));

        return newFood;
    }

    private void render() {
        clearScreen();

        StringBuilder display = new StringBuilder();

        // Construire la grille de jeu
        for (int row = 0; row < SCREEN_HEIGHT; row++) {
            for (int col = 0; col < SCREEN_WIDTH; col++) {
                Position currentPos = new Position(row, col);
                display.append(getCharAtPosition(currentPos));
            }
            display.append('\n');
        }

        // Afficher le jeu et le score
        System.out.println(display.toString());
        System.out.println("Score: " + score);
    }

    private char getCharAtPosition(Position position) {
        // Vérifier la nourriture
        if (position.equals(food)) {
            return FOOD_CHAR;
        }

        // Vérifier le serpent
        if (snake.collidesWith(position)) {
            return SNAKE_CHAR;
        }

        // Vérifier les murs
        if (isWall(position)) {
            return WALL_CHAR;
        }

        return EMPTY_CHAR;
    }

    private boolean isWall(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return row == 0 || row == SCREEN_HEIGHT - 1 ||
                col == 0 || col == SCREEN_WIDTH - 1;
    }

    private void clearScreen() {
        System.out.print(CLEAR_SCREEN);
        System.out.flush();
    }

    private void displayGameOver() {
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Score final: " + score);
    }
}
