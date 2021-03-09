package sin.display;

import sin.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Inventory {

    Game game;
    BufferedImage background;

    public Inventory(Game game) {
        this.game = game;
        BufferedImage inventory = null;
        try {
            inventory = ImageIO.read(new File("src/resources/display/inventory.png"));
        } catch (IOException e) {
            Game.dprint("Inventory texture not found!");
        }
        this.background = inventory;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, null);
    }

}
