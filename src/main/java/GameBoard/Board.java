package GameBoard;

import GUI.GameFinished;
import GUI.SettingUp;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class Board {


    private final StackPane root;
    private final Cell[][] grid;
    private EmptyCellPointer[] pointers;
    private GridPane gridRoot;
    private Player player;
    private Player computer;
    private SettingUp.DifficultyLevel difficultyLevel;
    private Circle animatedCircle;

    private final static int height = 6;
    private final static int width = 7;
    private final static int depth = 4;

    public Board(GameFinished gameFinished) {
        gridRoot = new GridPane();
        animatedCircle = new Circle(28.5);
        root = new StackPane(gridRoot, animatedCircle);
        grid = new Cell[height][width];
        pointers = new EmptyCellPointer[width];
        animatedCircle.setVisible(false);
        animatedCircle.setStroke(Color.BLACK);
        animatedCircle.setEffect(new InnerShadow(10, Color.BLACK));
        animatedCircle.setSmooth(true);
        gridRoot.setBackground(new Background(new BackgroundFill(Color.valueOf("#294052"), new CornerRadii(15), Insets.EMPTY)));
        gridRoot.setVgap(5);
        gridRoot.setHgap(5);
        gridRoot.setPadding(new Insets(10));
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
                gridRoot.add(grid[i][j], j, i);
                int finalJ = j;
                grid[i][j].setOnMouseClicked(event -> {
                    TranslateTransition translateTransition = new TranslateTransition(new Duration(400), animatedCircle);
                    translateTransition.setFromX(-204 + (68 * finalJ));
                    translateTransition.setFromY(-170);
                    translateTransition.setByY(68 * pointers[finalJ].getCellRow());
                    animatedCircle.setFill(player.getColor());
                    translateTransition.setOnFinished(actionEvent -> {
                        animatedCircle.setVisible(false);
                        addDisc(finalJ, player);
                        if (!check(gameFinished))
                            bestMove(gameFinished);
                    });
                    if (pointers[finalJ].getCellRow() >= 0) {
                        translateTransition.play();
                        animatedCircle.setVisible(true);
                    }
                });
                if (i == height - 1) pointers[j] = new EmptyCellPointer(i, grid[i][j]);
            }
    }

    private void bestMove(GameFinished gameFinished) {
        int bestMove = -Integer.MAX_VALUE;

        int bestJ = 0;
        int value;

        for (int j = 0; j < width; j++) {
            Cell cell = pointers[j].getCell();
            if (cell != null && cell.isEmpty()) {
                addDisc(j, computer);
                value = miniMax(difficultyLevel.getDepth(), -Integer.MAX_VALUE, Integer.MAX_VALUE, false);
                removeDisc(j);
                System.out.println("{" + j + "," + cell.getI() + "}" + "{score:" + value + "}");
                if (value > bestMove) {
                    bestJ = j;
                    bestMove = value;
                }
            }
        }
        TranslateTransition translateTransition = new TranslateTransition(new Duration(400), animatedCircle);
        translateTransition.setFromX(-204 + (68 * bestJ));
        translateTransition.setFromY(-170);
        translateTransition.setByY(68 * pointers[bestJ].getCellRow());
        animatedCircle.setFill(computer.getColor());
        int finalBestJ = bestJ;
        translateTransition.setOnFinished(actionEvent -> {
            animatedCircle.setVisible(false);
            addDisc(finalBestJ, computer);
            check(gameFinished);
        });
        if (pointers[bestJ].getCellRow() >= 0) {
            translateTransition.play();
            animatedCircle.setVisible(true);
        }
    }

    private void showBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].isEmpty()) System.out.print("#\t");
                else System.out.print(grid[i][j].getPlayer() + "\t");
            }
            System.out.println();
        }
    }

    private int evaluation() {
        int score = 0;


        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {

                if (grid[i][j].isEmpty()) continue;

                int playerVertical = verticalCheck(i, i - 4, j, player);
                int playerHorizontalR = horizontalCheck(j, j + 4, i, player);
                int playerHorizontalL = horizontalCheck(j - 3, j + 1, i, player);
                int playerDiagonalRight = countDiagonal(i, j, 0, player);
                int playerDiagonalLeft = countDiagonal(i, j, 1, player);

                int computerVertical = verticalCheck(i, i - 4, j, computer);
                int computerHorizontalR = horizontalCheck(j, j + 4, i, computer);
                int computerHorizontalL = horizontalCheck(j - 3, j + 1, i, computer);
                int computerDiagonalRight = countDiagonal(i, j, 0, computer);
                int computerDiagonalLeft = countDiagonal(i, j, 1, computer);

                int emptyVertical = verticalCheck(i, i - 4, j, null);
                int emptyHorizontalR = horizontalCheck(j, j + 4, i, null);
                int emptyHorizontalL = horizontalCheck(j - 3, j + 1, i, null);
                int emptyDiagonalRight = countDiagonal(i, j, 0, null);
                int emptyDiagonalLeft = countDiagonal(i, j, 1, null);

                if (playerVertical == 3 && emptyVertical == 1) score -= 1000;
                if (playerHorizontalR == 3 && emptyHorizontalR == 1) score -= 1000;
                if (playerHorizontalL == 3 && emptyHorizontalL == 1) score -= 1000;
                if (playerDiagonalRight == 3 && emptyDiagonalRight == 1) score -= 1000;
                if (playerDiagonalLeft == 3 && emptyDiagonalLeft == 1) score -= 1000;


                if (playerVertical == 2 && emptyVertical == 2) score -= 100;
                if (playerHorizontalR == 2 && emptyHorizontalR == 2) score -= 100;
                if (playerHorizontalL == 2 && emptyHorizontalL == 2) score -= 100;
                if (playerDiagonalRight == 2 && emptyDiagonalRight == 2) score -= 100;
                if (playerDiagonalLeft == 2 && emptyDiagonalLeft == 2) score -= 100;


                if (playerVertical == 1 && emptyVertical == 3) score -= 1;
                if (playerHorizontalR == 1 && emptyHorizontalR == 3) score -= 1;
                if (playerHorizontalL == 1 && emptyHorizontalL == 3) score -= 1;
                if (playerDiagonalRight == 1 && emptyDiagonalRight == 3) score -= 1;
                if (playerDiagonalLeft == 1 && emptyDiagonalLeft == 3) score -= 1;


                if (computerVertical == 3 && emptyVertical == 1) score += 1000;
                if (computerHorizontalR == 3 && emptyHorizontalR == 1) score += 1000;
                if (computerHorizontalL == 3 && emptyHorizontalL == 1) score += 1000;
                if (computerDiagonalRight == 3 && emptyDiagonalRight == 1) score += 1000;
                if (computerDiagonalLeft == 3 && emptyDiagonalLeft == 1) score += 1000;


                if (computerVertical == 2 && emptyVertical == 2) score += 100;
                if (computerHorizontalR == 2 && emptyHorizontalR == 2) score += 100;
                if (computerHorizontalL == 2 && emptyHorizontalL == 2) score += 100;
                if (computerDiagonalRight == 2 && emptyDiagonalRight == 2) score += 100;
                if (computerDiagonalLeft == 2 && emptyDiagonalLeft == 2) score += 100;


                if (computerVertical == 1 && emptyVertical == 3) score += 1;
                if (computerHorizontalR == 1 && emptyHorizontalR == 3) score += 1;
                if (computerHorizontalL == 1 && emptyHorizontalL == 3) score += 1;
                if (computerDiagonalRight == 1 && emptyDiagonalRight == 3) score += 1;
                if (computerDiagonalLeft == 1 && emptyDiagonalLeft == 3) score += 1;

            }
        }

        return score;
    }

    private int verticalCheck(int startRow, int endRow, int col, Player player) {
        int counter = 0;
        if (endRow < 0) return counter;
        for (int i = startRow; i > endRow; i--) {
            Player cellPlayer = grid[i][col].getPlayer();
            if (cellPlayer == player)
                counter++;
        }
        return counter;
    }

    private int horizontalCheck(int startCol, int endCol, int row, Player player) {
        int counter = 0;
        if (endCol > width || startCol < 0) return counter;
        for (int j = startCol; j < endCol; j++) {
            Player cellPlayer = grid[row][j].getPlayer();
            if (cellPlayer == player) {
                counter++;
            }
        }
        return counter;
    }

    private int countDiagonal(int i, int j, int direction, Player player) {

        int pieces = 0;

        for (int x = 0; x < 4; x++) {
            if (direction == 0) {
                if (i - x >= 0 && j + x < width)
                    if (grid[i - x][j + x].getPlayer() == player) pieces += 1;
            } else {
                if (i - x >= 0 && j - x >= 0)
                    if (grid[i - x][j - x].getPlayer() == player) pieces += 1;
            }

        }
        return pieces;
    }

    public int miniMax(int depth, int alpha, int beta, boolean isMax) {


        Player winner = checkWinner();
        if (winner != null) return winner == player ? -Integer.MAX_VALUE : Integer.MAX_VALUE;
        if (finished()) return 0;
        if (depth == 0) return evaluation();


        if (isMax) {
            int bestMove = -Integer.MAX_VALUE;
            int val;

            for (int j = 0; j < width; j++) {
                Cell cell = pointers[j].getCell();
                if (cell != null && cell.isEmpty()) {
                    addDisc(j, computer);
                    val = miniMax(depth - 1, alpha, beta, false);
                    bestMove = Math.max(bestMove, val);
                    removeDisc(j);
                    alpha = Math.max(alpha, val);
                    if (beta <= alpha)
                        break;

                }
            }

            return bestMove;
        } else {

            int bestMove = Integer.MAX_VALUE;
            int val;

            for (int j = 0; j < width; j++) {
                Cell cell = pointers[j].getCell();
                if (cell != null && cell.isEmpty()) {
                    addDisc(j, this.player);
                    val = miniMax(depth - 1, alpha, beta, true);
                    bestMove = Math.min(bestMove, val);
                    removeDisc(j);
                    beta = Math.min(beta, val);
                    if (beta <= alpha)
                        break;
                }
            }
            return bestMove;
        }

    }

    private boolean check(GameFinished gameFinished) {

        Player winner = checkWinner();
        if (finished()) gameFinished.show(GameFinished.GameStatus.Tie);
        if (winner == null) return false;
        if (winner == player) gameFinished.show(GameFinished.GameStatus.Won);
        else if (winner == computer) gameFinished.show(GameFinished.GameStatus.Lost);
        return true;

    }

    private Player checkWinner() {

        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width - 3; j++) {
                Player currentPlayer = grid[i][j].getPlayer();
                if (currentPlayer != null
                        && currentPlayer == grid[i][j + 1].getPlayer()
                        && currentPlayer == grid[i][j + 2].getPlayer()
                        && currentPlayer == grid[i][j + 3].getPlayer()) {
                    return currentPlayer;
                }
            }
        }

        for (int i = height - 1; i >= 3; i--) {
            for (int j = 0; j < width; j++) {
                Player currentPlayer = grid[i][j].getPlayer();
                if (currentPlayer != null
                        && currentPlayer == grid[i - 1][j].getPlayer()
                        && currentPlayer == grid[i - 2][j].getPlayer()
                        && currentPlayer == grid[i - 3][j].getPlayer())
                    return currentPlayer;
            }
        }

        for (int i = height - 1; i >= 3; i--) {
            for (int j = 0; j < width; j++) {
                Player currentPlayer = grid[i][j].getPlayer();

                if (currentPlayer != null && j < width - 3
                        && grid[i - 1][j + 1].getPlayer() == currentPlayer
                        && grid[i - 2][j + 2].getPlayer() == currentPlayer
                        && grid[i - 3][j + 3].getPlayer() == currentPlayer) return currentPlayer;

                if (currentPlayer != null && j >= 3
                        && grid[i - 1][j - 1].getPlayer() == currentPlayer
                        && grid[i - 2][j - 2].getPlayer() == currentPlayer
                        && grid[i - 3][j - 3].getPlayer() == currentPlayer) return currentPlayer;
            }
        }

        return null;
    }

    private boolean finished() {
        for (EmptyCellPointer cell : pointers)
            if (cell.getCell() != null) return false;
        return true;
    }

    public void addDisc(int j, Player player) {
        if (pointers[j].getCell() == null) return;
        pointers[j].getCell().acceptDisc(player);
        int currentRow = pointers[j].getCellRow();
        if (currentRow + 1 < height) pointers[j].getCell().linkWith(grid[currentRow + 1][j]);
        if (j + 1 < width) pointers[j].getCell().linkWith(grid[currentRow][j + 1]);
        if (j - 1 >= 0) pointers[j].getCell().linkWith(grid[currentRow][j - 1]);
        if (currentRow - 1 >= 0) pointers[j].setNextEmptyCell(currentRow - 1, grid[currentRow - 1][j]);
        else pointers[j].setNextEmptyCell(-4, null);
    }

    public void removeDisc(int j) {
        int currentRow;
        if (pointers[j].getCell() == null) currentRow = 0;
        else currentRow = pointers[j].getCellRow() + 1;
        pointers[j].setNextEmptyCell(currentRow, grid[currentRow][j]);
        pointers[j].getCell().empty();
    }

    public void reset() {
        for (int j = 0; j < width; j++)
            while (!(pointers[j].getCellRow() == height - 1))
                removeDisc(j);
    }


    public void setPlayers(Player player) {
        this.player = player;
        this.computer = player == Player.RED ? Player.YELLOW : Player.RED;
    }

    public void setDifficultyLevel(SettingUp.DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Pane getGridRoot() {
        return root;
    }

    public void start(GameFinished gameFinished) {
        bestMove(gameFinished);
        check(gameFinished);
    }
}
