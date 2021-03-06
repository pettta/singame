package sin.mundus.materia.entity;

import sin.Game;
import sin.lib.EntityType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WormBullet extends Entity {

    int life;
    BufferedImage img;

    public WormBullet(float x, float y, Game game, BufferedImage img) {
        super(x, y, 8, 8, EntityType.Projectile, game);
        int life = 0;
        this.img = img;
        this.speed = 10;
    }

    @Override
    public void tick() {
        life++;
        x += velX;
        y += velY;
        hb.x = (int) x;
        hb.y = (int) y;
        if(life >= 200) {
            handler.delEnt(this);

        }

    }

    public void render(Graphics g) {

    }

    @Override
    public void renderTop(Graphics g) {
        g.drawImage(img, (int) x, (int) y, null);

    }

}
