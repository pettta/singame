package sin.mundus;

import sin.Game;
import sin.materia.sprite.SpriteSheet;
import sin.materia.tile.Tile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Map {

    int[][] mapInts;
    LinkedList<Tile> tiles = new LinkedList<Tile>();
    String mapLoc;
    SpriteSheet ss;
    Game game;

    public Map(Game game, String mapLoc, String tilesetLoc) {
        this.game = game;
        this.mapLoc = mapLoc;
        this.ss = new SpriteSheet("tiles/" + tilesetLoc);
        populateMap();
        generateTiles();

        for(int y = 0; y < mapInts.length; y++) {
            for(int x = 0; x < mapInts[y].length; x++) {
                System.out.print(mapInts[y][x]);
            }
        }

    }

    public void generateTiles() {
        int w = ss.getRowCount();
        int h = ss.getColCount();

    }

    public void populateMap(){
        ArrayList<int[]> list = new ArrayList<int[]>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/resources/maps/" + mapLoc));
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowStrings = line.split(",");
                int[] rowInts = new int[rowStrings.length];
                for(int i = 0; i < rowStrings.length; i++) {
                    rowInts[i] = Integer.parseInt(rowStrings[i]);
                }
                list.add(rowInts);
            }
        } catch (FileNotFoundException e) {
            game.dprint("Error loading map from file: " + mapLoc);
            e.printStackTrace();
        } catch (IOException e) {
            game.dprint("Error loading map from file: " + mapLoc);
            e.printStackTrace();
        }
        mapInts = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            int[] row = list.get(i);
            mapInts[i] = row;
        }
    }

    public void render(Graphics g) {
        for(int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.render(g);
        }
    }

}
