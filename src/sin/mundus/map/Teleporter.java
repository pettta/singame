package sin.mundus.map;

import sin.Game;
import sin.Handler;
import sin.mundus.materia.entity.Entity;
import sin.mundus.materia.entity.WormShooter;

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void removeAllButPlayer() {
        for(int i = 0; i < game.handler.getList().size(); i++) {
            Entity ent = game.handler.getList().get(i);
            if(ent != game.player) {
                game.handler.delEnt(ent);
            }
        }
    }

    public void updateEntities() {
        System.out.println("AHJHHHHHHHHH");
        removeAllButPlayer();
        if(map.equals("testMap01.json")) {
            game.handler.addEnt(new WormShooter(493, 709, game));
            game.handler.addEnt(new WormShooter(386, 595, game));
            game.handler.addEnt(new WormShooter(160, 583, game));
            game.handler.addEnt(new WormShooter(273, 501, game));
            game.handler.addEnt(new WormShooter(109, 420, game));
            game.handler.addEnt(new WormShooter(291, 299, game));

        }
        if(map.equals("dungeon1.json")) {
            System.out.println("AHHH");
            game.handler.addEnt(new WormShooter(504, 1205, game));
            game.handler.addEnt(new WormShooter(662, 1209, game));
            game.handler.addEnt(new WormShooter(668, 833, game));
            game.handler.addEnt(new WormShooter(63, 1281, game));
            game.handler.addEnt(new WormShooter(222, 1279, game));
            game.handler.addEnt(new WormShooter(169, 831, game));
            game.handler.addEnt(new WormShooter(175, 705, game));
            game.handler.addEnt(new WormShooter(352, 282, game));
            game.handler.addEnt(new WormShooter(390, 157, game));
            game.handler.addEnt(new WormShooter(508, 116, game));
            game.handler.addEnt(new WormShooter(610, 222, game));


        } else {
            System.out.println("AHHHHH");
        }
    }

    public void doTeleport() {
        game.player.setX(xTo);
        System.out.println(xTo);
        game.player.setY(yTo);
        System.out.println(yTo);
        if(!(map == "same" || (map == game.map.map && tileset == game.map.tileset))) {
            game.map = new Map(game, map, tileset);
        }
        System.out.println(map);
        updateEntities();


    }

}
