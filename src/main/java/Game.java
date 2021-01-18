import Manager.GameManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application{


    private static GameManager gameManager;

    public static void main(String[] args) {
        gameManager = new GameManager();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameManager.startGame(primaryStage);
    }
}
