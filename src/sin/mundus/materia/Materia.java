package sin.mundus.materia;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Materia {

    protected float x, y;
    protected int width, height;
    protected BufferedImage image;
    public Rectangle hb;

    public Materia(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hb = new Rectangle((int) x, (int) y, width, height);
    }

    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

    public void renderTop(Graphics g) {

    }

    public void setX(float x) {
        this.x = x;
        this.hb.x = (int) x;
    }

    public void setY(float y) {
        this.y = y;
        this.hb.y = (int) y;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
        this.hb.x = (int) x;
        this.hb.y = (int) y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getXMid() {
        return x + (width / 2);
    }

    public float getYMid() {
        return y + (height / 2);
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

}
