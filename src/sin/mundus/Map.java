package sin.mundus;

import org.json.*;
import sin.Game;
import sin.materia.sprite.SpriteSheet;
import sin.materia.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Map {

    ArrayList<BufferedImage> tileImages;
    ArrayList<Tile> tiles;

    int width, height;
    Game game;
    String mapLoc;
    SpriteSheet ss;

    public Map(Game game, String mapLoc, String tilesetLoc) {
        this.game = game;
        this.mapLoc = mapLoc;
        this.ss = new SpriteSheet("tiles/" + tilesetLoc);
        tileImages = new ArrayList<BufferedImage>();
        tiles = new ArrayList<Tile>();
        generateTiles();
        populateMap();

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

    public static JSONObject parseJSONFile(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

    /**
     * Layer 1 through 3 of the map file are normal.
     * Layer 4 renders above entities.
     * Layer 5 is the collision markers.
     */
    public void populateMap() {

        JSONObject obj = null;
        try {
            obj = parseJSONFile("src/resources/maps/" + mapLoc);
        } catch(IOException e) {
            e.printStackTrace();
        }
        JSONArray layers = obj.getJSONArray("layers");
        height = obj.getInt("height");
        width = obj.getInt("width");
        JSONArray[] data = new JSONArray[7];
        for (int i = 0; i < layers.length(); i++) {
            JSONObject oi = (JSONObject) layers.getJSONObject(i);
            data[i] = oi.getJSONArray("data");
        }
        if(data.length != 7) {
            System.out.println("Map " + mapLoc + " does not have the correct number of layers!");
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
                boolean collide = data[6].getInt(j) == 1 ? true : false;
                tiles.add(new Tile(x*16, y*16, collide, image1, image2, image3, image4, image5, image6));
                j++;
            }
        }
    }

    public void render(Graphics g) {
        for(int i = 0; i < tiles.size(); i++) {
            tiles.get(i).render(g);
        }

    }

    public void renderTop(Graphics g) {
        for(int i = 0; i < tiles.size(); i++) {
            tiles.get(i).renderTop(g);
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

}
