package invaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class GameData{
    private JSONObject gameData;
    private JSONArray enemiesData;
    private JSONArray bunkersData;
    private JSONObject playerData;

    public GameData(String config) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(config)) {
            // 使用JSONParser解析JSON文件并将其转换为JSONObject
            JSONObject jsonData = (JSONObject) parser.parse(reader);

            // 现在您可以访问JSON数据并提取所需的信息
            this.gameData = (JSONObject) jsonData.get("Game");
            this.playerData = (JSONObject) jsonData.get("Player");
            this.bunkersData = (JSONArray) jsonData.get("Bunkers");
            this.enemiesData = (JSONArray) jsonData.get("Enemies");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getGameData() {
        return gameData;
    }

    public JSONObject getPlayerData() {
        return playerData;
    }

    public JSONArray getEnemiesData() {
        return enemiesData;
    }

    public JSONArray getBunkersData() {
        return bunkersData;
    }
}