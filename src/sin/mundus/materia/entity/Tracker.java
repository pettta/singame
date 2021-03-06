package sin.mundus.materia.entity;

import sin.Game;
import sin.lib.EntityType;
import sin.lib.Vector;

import java.awt.*;

public class Tracker extends Entity {

    int adjustCounter;

    public Tracker(float x, float y, int width, int height, float speed, EntityType type, int adjustCounter, Game game) {
        super(x, y, width, height, type, game);
        this.speed = speed;
        this.adjustCounter = adjustCounter;

    }

    @Override
    public void tick() {
        if(adjustCounter >= 0) {
            Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), speed);
            velX = (int) vector.getHorizComp();
            velY = (int) vector.getVertComp();
            adjustCounter = 0;
        } else {
            adjustCounter++;
        }
        super.tick();

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        super.render(g);

    }

}
