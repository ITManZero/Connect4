package Manager;

import GUI.GameFinished;
import GUI.GamePlay;
import GUI.SettingUp;
import com.sun.tools.javac.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class GameManager {

    private GameFinished gameFinished;
    private SettingUp settingUpWindow;
    private GamePlay gamePlayWindow;
    private static SettingUp.DifficultyLevel difficultyLevel;

    public void startGame(Stage primaryStage) throws Exception {
        this.gameFinished = new GameFinished(primaryStage);
        this.settingUpWindow = new SettingUp(primaryStage, List.of(difficultyLevel.EASY, difficultyLevel.NORMAL, difficultyLevel.HARD));
        this.gamePlayWindow = new GamePlay(gameFinished);
        this.settingUpWindow.getStartGame().setOnMouseClicked(event -> {
            this.gamePlayWindow.setPlayerColor(settingUpWindow.getPlayerColor());
            this.gamePlayWindow.setDifficultyLevel(settingUpWindow.getDifficulty());
            this.gamePlayWindow.start(gameFinished);
            this.gamePlayWindow.switchScene(primaryStage);
        });
        this.gameFinished.getNewGame().setOnMouseClicked(event -> {
            gameFinished.close();
            gamePlayWindow.resetGamePlay();
            this.gamePlayWindow.start(gameFinished);
        });

        this.gameFinished.getSettings().setOnMouseClicked(event -> {
            primaryStage.close();
            gamePlayWindow.resetGamePlay();
            primaryStage.setScene(settingUpWindow.getScene());
            primaryStage.show();
        });
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit Request", ButtonType.YES, ButtonType.NO);
        primaryStage.setOnCloseRequest(windowEvent -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.NO) windowEvent.consume();
        });
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
