package sin.materia.tile;

import sin.materia.Materia;

import java.awt.image.BufferedImage;

public abstract class Tile extends Materia {

    protected int depth;
    protected boolean collides;

    public Tile(float x, float y, int depth, boolean collides, BufferedImage image) {
        super(x, y, 16, 16);
        this.image = image;
        this.depth = depth;
        this.collides = collides;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isCollides() {
        return collides;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setCollides(boolean col) {
        this.collides = col;
    }

}
