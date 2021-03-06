package sin.save;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Lib;
import sin.mundus.map.Map;
import sin.mundus.map.Teleporter;
/**
 * Name: SaveHandler.java
 * Purpose: Handles saves.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class SaveHandler {

    public float x, y;
    public String map, tileset;

    public static void loadSave(Game game, String save) {
        JSONObject obj = Lib.loadJSON("src/saves/" + save);
        game.player.setX(obj.getFloat("x"));
        game.player.setY(obj.getFloat("y"));
        Map map = new Map(game, obj.getString("map"), obj.getString("tileset"));
        game.map = map;
        Teleporter.updateEntities(game.map);
    }

    public static void save(Game game, String save) {
        JSONObject obj = new JSONObject();
        obj.put("x", game.player.getX());
        obj.put("y", game.player.getY());
        obj.put("map", game.map.map);
        obj.put("tileset", game.map.tileset);
        Lib.writeJSON(obj, "src/saves/" + save);
    }


}
