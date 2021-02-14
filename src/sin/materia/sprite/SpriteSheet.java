package sin.materia.sprite;

import sin.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage sheet;
    private int width;
    private int height;

    public SpriteSheet(String loc) {
        BufferedImage sheet = null;
        String source = "src/resources/";
        try {
            sheet = ImageIO.read(new File(source + loc));
        } catch (IOException e) {
            Game.dprint("No such thing exists: " + source + loc);
        }
        this.sheet = sheet;
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
