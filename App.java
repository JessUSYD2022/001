package invaders;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;
import org.json.simple.JSONObject;

import java.util.Map;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Map<String, String> params = getParameters().getNamed();
        GameEngine model = new GameEngine("/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/config.json");

        // read the config to get GameWindow size
        GameData data = new GameData("/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/config.json");
        JSONObject gameData = data.getGameData();
        JSONObject size = (JSONObject) gameData.get("size");
        int sizeX = Integer.parseInt(size.get("x").toString());
        int sizeY = Integer.parseInt(size.get("y").toString());
        GameWindow window = new GameWindow(model, sizeX, sizeY);
        window.setModel(model);
        model.setWindow(window);
        window.run();
        model.addObserver(window);


        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();

    }
}
