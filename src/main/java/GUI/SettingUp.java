package GUI;


import GameBoard.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;


public class SettingUp {

    public enum DifficultyLevel {
        EASY("Easy", 1), NORMAL("Normal", 3), HARD("Hard", 5);


        private final String level;
        private final int depth;

        DifficultyLevel(String level, int depth) {
            this.depth = depth;
            this.level = level;
        }

        public int getDepth() {
            return depth;
        }

        @Override
        public String toString() {
            return level;
        }
    }

    private Label disDiff;
    private ComboBox<DifficultyLevel> difficulty;
    private Button redDisc;
    private Button yellowDisc;
    private Button startGame;
    private Player currentPlayer;
    private Scene scene;


    public SettingUp(Stage primaryStage, List<DifficultyLevel> difficulties) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        HBox hBox2 = new HBox();
        difficulty = new ComboBox<>();
        difficulty.getItems().addAll(difficulties);


        redDisc = new Button();
        yellowDisc = new Button();
        startGame = new Button("start game");
        disDiff = new Label("difficulty: ");
        Circle redCir = new Circle(10);
        Circle yellowCir = new Circle(10);

        redDisc.setPrefSize(40, 40);
        redCir.setFill(Color.INDIANRED);
        redDisc.setGraphic(redCir);
        redCir.setStroke(Color.BLACK);
        redCir.setStrokeWidth(1.5f);
        redCir.setOpacity(0.5f);
        redDisc.setOnMouseClicked(event -> {
            redCir.setOpacity(1f);
            yellowCir.setOpacity(0.5f);
            currentPlayer = Player.RED;
        });
        redDisc.setOnMouseExited(event -> {
            if (!redDisc.isFocused()) redCir.setOpacity(0.5f);
        });
        redDisc.setOnMouseEntered(event -> {
            redCir.setOpacity(1f);
        });

        yellowDisc.setPrefSize(40, 40);

        yellowCir.setFill(Color.GOLDENROD);
        yellowDisc.setGraphic(yellowCir);
        yellowCir.setStroke(Color.BLACK);
        yellowCir.setStrokeWidth(1.5f);
        yellowCir.setOpacity(0.5f);
        yellowDisc.setOnMouseClicked(event -> {
            yellowCir.setOpacity(1f);
            redCir.setOpacity(0.5f);
            currentPlayer = Player.YELLOW;
        });
        yellowDisc.setOnMouseExited(event -> {
            if (!yellowDisc.isFocused()) yellowCir.setOpacity(0.5f);
        });
        yellowDisc.setOnMouseEntered(event -> {
            yellowCir.setOpacity(1f);
        });

        currentPlayer = Player.RED;

        startGame.setPrefSize(85, 30);

        difficulty.getSelectionModel().selectFirst();

        hBox2.getChildren().addAll(disDiff, difficulty);
        vBox.getChildren().addAll(hBox2, hBox, startGame);
        hBox.getChildren().addAll(redDisc, yellowDisc);

        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(35);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(35);

        scene = new Scene(vBox, 400, 300);
        primaryStage.setScene(scene);
    }

    public Button getStartGame() {
        return startGame;
    }

    public Player getPlayerColor() {
        return currentPlayer;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty.getSelectionModel().getSelectedItem();
    }

    public Scene getScene() {
        return scene;
    }
}
