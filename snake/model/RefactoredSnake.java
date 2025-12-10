package snake.model;

public class RefactoredSnake {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.start();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution du jeu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}