package sin.mundus.map;

import org.json.*;
import sin.Game;
import sin.lib.Lib;
import sin.mundus.materia.sprite.SpriteSheet;
import sin.mundus.materia.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Map {

    ArrayList<BufferedImage> tileImages;
    ArrayList<Tile> tiles;
    ArrayList<Teleporter> teles;

    int width, height;
    Game game;
    public String map, tileset;
    SpriteSheet ss;

    public static BufferedImage collisionBox;

    public Map(Game game, String mapLoc, String tilesetLoc) {
        this.game = game;
        this.map = mapLoc;
        this.tileset = tilesetLoc;
        this.ss = new SpriteSheet("tiles/" + tilesetLoc);
        tileImages = new ArrayList<BufferedImage>();
        tiles = new ArrayList<Tile>();
        teles = new ArrayList<Teleporter>();
        generateTiles();
        populateMap();
        collisionBox = tileImages.get(0);

    }

    public void generateTiles() {
        int h = ss.getRowCount();
        int w = ss.getColCount();
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                tileImages.add(ss.grabImage(x, y, 16, 16));
            }
        }
        System.out.println("SIZE: " + tileImages.size());
    }



    /**
     * Layer 1 through 4 of the map file are normal.
     * Layers 5 and 6 render above entities.
     * Layer 7 is the collision markers.
     */
    public void populateMap() {

        JSONObject obj = Lib.loadJSON("src/resources/maps/" + map);
        JSONArray layers = obj.getJSONArray("layers");

        height = obj.getInt("height");
        width = obj.getInt("width");
        JSONArray[] data = new JSONArray[7];
        for (int i = 0; i < layers.length(); i++) {
            JSONObject oi = (JSONObject) layers.getJSONObject(i);
            data[i] = oi.getJSONArray("data");
        }
        if(obj.has("teleporters")) {
            JSONArray teleporters = obj.getJSONArray("teleporters");
            for (int i = 0; i < teleporters.length(); i++) {
                JSONObject oi = (JSONObject) teleporters.getJSONObject(i);
                int x, y, xTo, yTo, width, height;
                if (oi.getBoolean("useTiles")) {
                    x = oi.getInt("x") * 16;
                    y = oi.getInt("y") * 16;
                    xTo = oi.getInt("xTo") * 16;
                    yTo = oi.getInt("yTo") * 16;
                    width = oi.getInt("width") * 16;
                    height = oi.getInt("height") * 16;
                } else {
                    x = oi.getInt("x");
                    y = oi.getInt("y");
                    xTo = oi.getInt("xTo");
                    yTo = oi.getInt("yTo");
                    width = oi.getInt("width");
                    height = oi.getInt("height");
                }
                String map = oi.getString("map");
                String tileset = oi.getString("tileset");
                teles.add(new Teleporter(map, tileset, x, y, xTo, yTo, width, height, game));
            }
        }
        if(data.length != 7) {
            System.out.println("Map " + map + " does not have the correct number of layers!");
        }
        int j = 0;
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                BufferedImage image1 = (data[0].getInt(j) == 0) ? null : (tileImages.get(data[0].getInt(j) - 1));
                BufferedImage image2 = (data[1].getInt(j) == 0) ? null : (tileImages.get(data[1].getInt(j) - 1));
                BufferedImage image3 = (data[2].getInt(j) == 0) ? null : (tileImages.get(data[2].getInt(j) - 1));
                BufferedImage image4 = (data[3].getInt(j) == 0) ? null : (tileImages.get(data[3].getInt(j) - 1));
                BufferedImage image5 = (data[4].getInt(j) == 0) ? null : (tileImages.get(data[4].getInt(j) - 1));
                BufferedImage image6 = (data[5].getInt(j) == 0) ? null : (tileImages.get(data[5].getInt(j) - 1));
                boolean collide = data[6].getInt(j) == 1;
                tiles.add(new Tile(x*16, y*16, collide, image1, image2, image3, image4, image5, image6));
                j++;
            }
        }
    }

    public void renderCollision(Graphics g) {

    }

    public void renderTeleporters(Graphics g) {
        for(int i = 0; i < teles.size(); i++) {
            g.setColor(Color.blue);
            Teleporter cur = teles.get(i);
            g.drawRect(cur.getX(), cur.getY(), cur.getWidth(), cur.getHeight());
        }
    }

    // TODO Organize tiles in a double array for quick more efficient access.
    public void render(Graphics g, int minX, int maxX, int minY, int maxY) {
        for(int i = 0; i < tiles.size(); i++) {
            Tile curTile = tiles.get(i);
            if(curTile.getX() > minX - 16 && curTile.getX() < maxX && curTile.getY() > minY - 16 && curTile.getY() < maxY) {
                tiles.get(i).render(g);
            }
        }
        // renderTeleporters(g);

    }

    public void renderTop(Graphics g, int minX, int maxX, int minY, int maxY) {
        for(int i = 0; i < tiles.size(); i++) {
            Tile curTile = tiles.get(i);
            if(curTile.getX() > minX - 16 && curTile.getX() < maxX && curTile.getY() > minY - 16 && curTile.getY() < maxY) {
                tiles.get(i).renderTop(g);
            }
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Teleporter> getTeleporters() {
        return teles;
    }

}
