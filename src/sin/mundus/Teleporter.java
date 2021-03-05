package sin.mundus;

import sin.Game;

import java.awt.*;

public class Teleporter {

    private String map, tileset;
    private int x, y, xTo, yTo, width, height;
    private Game game;

    public Teleporter(String map, String tileset, int x, int y, int xTo, int yTo, int width, int height, Game game) {
        this.map = map;
        this.tileset = tileset;
        this.x = x;
        this.y = y;
        this.xTo = xTo;
        this.yTo = yTo;
        this.width = width;
        this.height = height;
        this.game = game;
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
        game.player.setX(xTo);
        System.out.println(xTo);
        game.player.setY(yTo);
        System.out.println(yTo);
        if(map != "same") {
            game.map = new Map(game, map, tileset);
        }
    }

}
