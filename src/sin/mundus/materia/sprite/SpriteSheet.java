package sin.mundus.materia.sprite;

import sin.Game;
import sin.lib.Lib;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * Name: SpriteSheet.java
 * Purpose: Simply loads a sprite sheet.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class SpriteSheet {

    private BufferedImage sheet;
    private int width;
    private int height;

    public SpriteSheet(String loc) {
        BufferedImage sheet = null;
        String source = "src/resources/";
        this.sheet = Lib.getImage(source + loc);
        this.width = this.sheet.getWidth();
        this.height = this.sheet.getHeight();
    }

    public SpriteSheet(BufferedImage ss) {
        this.sheet = ss;
    }

    // Everything is wack here.
    public BufferedImage grabImage(int col, int row, int width, int height) {
        BufferedImage img = sheet.getSubimage(col * 16, row * 16, width, height);
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRowCount() {
        return height / 16;
    }

    public int getColCount() {
        return width / 16;
    }

}
