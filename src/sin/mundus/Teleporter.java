package sin.mundus;

import sin.Game;

import java.awt.*;

public class Teleporter {

    private String map, tileset;
    private int x, y, width, height;
    private Game game;

    public Teleporter(String map, String tileset, int x, int y, int width, int height, Game game) {
        this.map = map;
        this.tileset = tileset;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public String getMap() {
        return map;
    }

    public String getTileset() {
        return tileset;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void doTeleport() {
        game.player.setX(x);
        game.player.setY(y);
        game.map = new Map(game, map, tileset);
    }

}
