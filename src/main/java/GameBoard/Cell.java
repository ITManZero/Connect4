package GameBoard;

import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Objects;

public class Cell extends Circle {

    private int i;
    private int j;
    private Player player;
    private ArrayList<Cell> children;

    public Cell(int i, int j) {
        super(30);
        this.i = i;
        this.j = j;
        children = new ArrayList<>();
        setFill(Color.valueOf("#89979E"));
        setStroke(Color.BLACK);
        setStrokeWidth(3);
        setEffect(new InnerShadow(18, Color.BLACK));
        setSmooth(true);

    }

    public void linkWith(Cell cell) {
        Objects.requireNonNull(cell);
        if (!samePlayer(cell)) return;
        addChildren(cell);
        cell.addChildren(this);
    }

    public void acceptDisc(Player player) {
        this.player = player;
        setFill(player.getColor());
    }


    private void addChildren(Cell cell) {
        children.add(cell);
    }

    public ArrayList<Cell> getChildren() {
        return children;
    }

    public boolean samePlayer(Object o) {
        Cell cell = (Cell) o;
        return ((Cell) o).player == player;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return i == cell.i &&
                j == cell.j;
    }



    public void empty() {
        this.player = null;
        setFill(Color.valueOf("#89979E"));
        children.clear();
    }

    public boolean isEmpty() {
        return player == null;
    }


    public Player getPlayer() {
        return player;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
