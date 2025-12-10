package snake.model;

enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1);

    private final int deltaRow;
    private final int deltaCol;

    Direction(int deltaCol, int deltaRow) {
        this.deltaCol = deltaCol;
        this.deltaRow = deltaRow;
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaCol() {
        return deltaCol;
    }

    public Direction getOpposite() {
        switch (this) {
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            case UP: return DOWN;
            case DOWN: return UP;
            default: return this;
        }
    }
}