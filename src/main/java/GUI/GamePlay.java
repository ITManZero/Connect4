package GUI;


import GameBoard.Board;
import GameBoard.Player;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GamePlay {

    private Scene scene;
    private Board gameBoard;
    private Button reset;

    public GamePlay(GameFinished gameFinished) {
        this.gameBoard = new Board(gameFinished);
        this.gameBoard.setPlayers(Player.RED);
        this.reset = new Button("reset");

        HBox hBox = new HBox();
        VBox vBox = new VBox();
        reset.setPrefSize(85, 25);
        vBox.getChildren().addAll(reset);
        vBox.setPadding(new Insets(10, 0, 0, 30));
        vBox.setSpacing(30);
        hBox.getChildren().addAll(gameBoard.getGridRoot(), vBox);
        hBox.setPadding(new Insets(40));
        hBox.setFillHeight(false);


        scene = new Scene(hBox, 650, 510);

        reset.setOnMouseClicked(mouseEvent -> {
            gameBoard.reset();
            gameBoard.start(gameFinished);
        });

    }

    public void switchScene(Stage primaryStage) {
        primaryStage.setTitle("4InRow");
        primaryStage.close();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPlayerColor(Player player) {
        gameBoard.setPlayers(player);
    }

    public void setDifficultyLevel(SettingUp.DifficultyLevel difficultyLevel) {
        gameBoard.setDifficultyLevel(difficultyLevel);
    }

    public void start(GameFinished gameFinished) {
        gameBoard.start(gameFinished);
    }

    public void resetGamePlay() {
        gameBoard.reset();
    }
}
