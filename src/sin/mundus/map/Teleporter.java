package sin.mundus.map;

import sin.Game;
import sin.mundus.materia.entity.*;

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

    public static void removeAllButPlayer(Game game) {
        for(int i = 0; i < game.handler.getList().size(); i++) {
            Entity ent = game.handler.getList().get(i);
            if(ent != game.player) {
                game.handler.delEnt(ent);
            }
        }
    }

    public static void updateEntities(Map map) {
        removeAllButPlayer(map.game);
        Game game = map.game;
        System.out.println(map.map);
        if(map.map.equals("testMap01.json")) {
            System.out.println("Ajjjjjjjjjjjjj");
            game.handler.addEnt(new EntityWormShooter(493, 709, game));
            game.handler.addEnt(new EntityWormShooter(386, 595, game));
            game.handler.addEnt(new EntityWormShooter(160, 583, game));
            game.handler.addEnt(new EntityWormShooter(273, 501, game));
            game.handler.addEnt(new EntityWormShooter(109, 420, game));
            game.handler.addEnt(new EntityWormShooter(291, 299, game));
            game.handler.addEnt(new EntityShopkeeper(500, 780, 2, game));
            game.handler.addEnt(new EntityNPC(400, 700, 2, game));
            game.handler.addEnt(new EntityGoat(494, 443, game));
            game.audioPlayer.playAudio("DungeonTrack6.wav");
        } else if(map.map.equals("dungeon1.json")) {

            game.handler.addEnt(new EntityWormShooter(504, 1205, game));
            game.handler.addEnt(new EntityWormShooter(662, 1209, game));
            game.handler.addEnt(new EntityWormShooter(668, 833, game));
            game.handler.addEnt(new EntityWormShooter(63, 1281, game));
            game.handler.addEnt(new EntityWormShooter(222, 1279, game));
            game.handler.addEnt(new EntityWormShooter(169, 831, game));
            game.handler.addEnt(new EntityWormShooter(175, 705, game));
            game.handler.addEnt(new EntityWormShooter(390, 157, game));
            game.handler.addEnt(new EntityWormBoss(480, 66, game));
            game.audioPlayer.playAudio("DungeonTrack1.wav");

        } else if(map.map.equals("dungeon2.json")) {
            game.audioPlayer.playAudio("DungeonTrack5.wav");

        } else if(map.map.equals("village2.json")) {
            game.audioPlayer.playAudio("DungeonTrack2.wav");

        } else if(map.equals("house.json")) {
            game.handler.addEnt(new EntityNPC(184, 140, 1, game));
            game.audioPlayer.playAudio("DungeonTrack4.wav");

        } else if(map.equals("testMap02.json")) {
            game.audioPlayer.playAudio("zachmediocretheme.wav");

        } else if(map.equals("playerhouse.json")) {
            game.audioPlayer.playAudio("DungeonTrack4.wav");

        } else if(map.map.equals("connectormap.json")) {
            System.out.println("ASHFHHFHJFHJFJJFJFJFJFJFJFJ");
            game.audioPlayer.playAudio("zachmediocretheme.wav");
            game.handler.addEnt(new EntityDragon(1684, 1220, game));

        } else {
            game.audioPlayer.playAudio("zachmediocretheme.wav");

        }
    }

    public void doTeleport() {
        String lastMap = game.map.map;
        game.player.setX(xTo);
        System.out.println(xTo);
        game.player.setY(yTo);
        System.out.println(yTo);
        if(!(map == "same" || (map == game.map.map && tileset == game.map.tileset))) {
            game.map = new Map(game, map, tileset);
        }
        System.out.println(map);
        if(!lastMap.equals(game.map.map)) {
            updateEntities(game.map);

        }


    }

}
