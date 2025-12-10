package snake.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Snake {
    private final LinkedList<Position> body;
    private Direction currentDirection;

    public Snake(Position startPosition, Direction initialDirection) {
        this.body = new LinkedList<>();
        this.currentDirection = initialDirection;

        // Créer un serpent de 3 segments
        body.add(startPosition);
        body.add(startPosition.move(initialDirection.getOpposite()));
        body.add(startPosition.move(initialDirection.getOpposite()).move(initialDirection.getOpposite()));
    }

    public Position getHead() {
        return body.getFirst();
    }

    public List<Position> getBody() {
        return Collections.unmodifiableList(body);
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setDirection(Direction newDirection) {
        // Empêcher le serpent de faire demi-tour
        if (newDirection != currentDirection.getOpposite()) {
            this.currentDirection = newDirection;
        }
    }

    public void move(boolean grow) {
        Position newHead = getHead().move(currentDirection);
        body.addFirst(newHead);

        if (!grow) {
            body.removeLast();
        }
    }

    public boolean collidesWith(Position position) {
        return body.contains(position);
    }

    public boolean collidesWithSelf() {
        Position head = getHead();
        return body.subList(1, body.size()).contains(head);
    }
}

