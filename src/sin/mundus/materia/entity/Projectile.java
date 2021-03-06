package sin.mundus.materia.entity;

import sin.Game;
import sin.lib.EntityType;

import java.awt.*;

public class Projectile extends Entity {

    int life;


    public Projectile(float x, float y, int width, int height, EntityType type, Game game) {
        super(x, y, width, height, type, game);
        int life = 0;


    }

    @Override
    public void tick() {
        super.tick();

        life++;
        if(life >= 200) {
            handler.delEnt(this);

        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        super.render(g);

    }

}
