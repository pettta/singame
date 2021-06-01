package sin.mundus.materia.tile;

import sin.mundus.materia.Materia;
import sin.mundus.map.Map;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Name: Tile.java
 * Purpose: Base class for a tile. Saves images to be displayed and whether it collides with player.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class Tile extends Materia {

    protected boolean collides;
    protected BufferedImage image2, image3, image4, image5, image6;

    public Tile(float x, float y, boolean collides, BufferedImage image1, BufferedImage image2, BufferedImage image3, BufferedImage image4, BufferedImage image5, BufferedImage image6) {
        super(x, y, 16, 16);
        this.image = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.image6 = image6;
        this.collides = collides;
        this.hb = new Rectangle((int) x, (int) y, 16, 16);
    }

    public boolean isCollides() {
        return collides;
    }

    public void setCollides(boolean col) {
        this.collides = col;
    }

    public void render(Graphics g) {
        if(image != null) {
            g.drawImage(image, (int) x, (int) y, null);
        }
        if(image2 != null) {
            g.drawImage(image2, (int) x, (int) y, null);
        }
        if(image3 != null) {
            g.drawImage(image3, (int) x, (int) y, null);
        }
        if(image4 != null) {
            g.drawImage(image4, (int) x, (int) y, null);
        }
    }

    public void renderTop(Graphics g) {
        if(image5 != null) {
            g.drawImage(image5, (int) x, (int) y, null);
        }
        if(image6 != null) {
            g.drawImage(image6, (int) x, (int) y, null);
        }
    }

    public void drawCollisionBox(Graphics g) {
        if(collides) {
            g.drawImage(Map.collisionBox, (int) x, (int) y, null);
        }
    }

}
