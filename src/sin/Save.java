package sin;

import org.json.JSONObject;
import sin.lib.Lib;
import sin.mundus.map.Map;

public class Save {

    public float x, y;
    public String map, tileset;

    // THIS CLASSS IS NOT USED!! SO FAR? idk
    // rn i just use the menu class

    public Save(String saveName) {
        JSONObject obj = Lib.loadJSON("src/saves/" + saveName);
        x = obj.getFloat("x");
        y = obj.getFloat("y");
        map = obj.getString("map");
        tileset = obj.getString("tileset");
    }


    public void load(Game game) {
        game.player.setPos(x, y);
        game.map = new Map(game, map, tileset);
    }

}
