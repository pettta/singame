package sin.mundus.materia.entity;

import sin.Game;
import sin.lib.Vector;

import java.awt.*;

public class Shooter extends Entity {

    int speed;
    int counter;

    public Shooter(float x, float y, int width, int height, int speed, EntityType type, Game game) {
        super(x, y, width, height, type, game);
        this.speed = speed;
        this.counter = 0;
    }

    @Override
    public void tick() {
        if(counter > 20) {
            Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), speed);
            Projectile proj = new Projectile(getXMid(), getYMid(), 16, 16, EntityType.Enemy, game);
            proj.setVelX(vector.getHorizComp());
            proj.setVelY(vector.getVertComp());
            handler.addEnt(proj);
            counter = 0;
        } else {
            counter++;
        }
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        super.render(g);

    }

}
