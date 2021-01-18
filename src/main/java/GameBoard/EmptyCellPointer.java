package GameBoard;

public class EmptyCellPointer {

    private int cellRow;
    private Cell cell;


    public EmptyCellPointer(int cellRow, Cell cell) {
        this.cell = cell;
        this.cellRow = cellRow;
    }

    public void setNextEmptyCell(int i, Cell cell) {
        this.cell = cell;
        this.cellRow = i;
    }

    public int getCellRow() {
        return cellRow;
    }

    public Cell getCell() {
        return cell;
    }
}
