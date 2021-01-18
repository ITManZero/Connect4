package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class GameFinished extends Stage {

    public enum GameStatus {
        Tie("Tie..!"), Lost("Lost..!"), Won("Won..!");

        private String status;

        GameStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    private Scene scene;
    private Label status;
    private VBox vBox;
    private Button newGame;
    private Button settings;

    public GameFinished(Stage primaryStage) {
        status = new Label();
        vBox = new VBox();
        scene = new Scene(vBox, 150, 200);
        newGame = new Button("New Game");
        newGame.setPrefSize(85, 20);
        settings = new Button("Back to Settings");
        settings.setPrefSize(120, 20);

        vBox.setSpacing(25);
        vBox.getChildren().addAll(status, newGame, settings);
        vBox.setAlignment(Pos.CENTER);
        setScene(scene);
        //initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(primaryStage);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit Request", ButtonType.YES, ButtonType.NO);
        setOnCloseRequest(windowEvent -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.NO) windowEvent.consume();
        });
    }


    public void show(GameStatus gameStatus) {
        status.setText(gameStatus.getStatus());
        setOpacity(0.85f);
        setResizable(false);
        show();
    }

    public Button getNewGame() {
        return newGame;
    }

    public Button getSettings() {
        return settings;
    }
}
