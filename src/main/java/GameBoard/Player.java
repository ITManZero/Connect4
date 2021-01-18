package GameBoard;

import javafx.scene.paint.Color;

public enum Player {

    RED(Color.INDIANRED), YELLOW(Color.GOLDENROD);

    private Color color;

    Player(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
