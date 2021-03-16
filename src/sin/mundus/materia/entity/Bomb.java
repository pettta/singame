package sin.mundus.materia.entity;

import sin.Game;
import sin.lib.Lib;
import sin.mundus.materia.sprite.Polysprite;
import sin.mundus.materia.tile.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb extends Entity {

    int life;
    int explosionProgress;
    Polysprite ps;
    BufferedImage image;
    BufferedImage preExplosion;

    public Bomb(float x, float y, Game game) {
        super(x, y, 16, 16, EntityType.Obstacle, game);
        int life = 0;
        preExplosion = Lib.getImage("src/resources/items/bomb.png");
        hb = new Rectangle((int) getXMid() - 20, (int) getYMid() - 20, 40, 40);
        ps = new Polysprite("entities/explosion.png", 10, 1, 32, 32);

    }

    public void doCollision() {
        for(int i = 0; i < game.map.getTiles().size(); i++) {
            Tile tile = game.map.getTiles().get(i);
            if(tile.isCollides() && getBounds().intersects(tile.hb)) {
                life = 20;
            }
        }

        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if(ent.getType() == EntityType.Enemy && ent.getBounds().intersects(getBounds())) {
                life = 20;
            }
        }
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;


        hb.x = (int) getXMid() - 20;
        hb.y = (int) getYMid() - 20;

        life++;
        if(life >= 20) {
           velX = 0;
           velY = 0;
           explosionProgress += 1;
           image = ps.getCurImage(explosionProgress / 2);
           if(explosionProgress >= 19) {
               for(int i = 0; i < handler.getList().size(); i++) {
                   Entity ent = handler.getList().get(i);
                   if(hb.intersects(ent.getBounds())) {
                       ent.health -= 200;

                   }
               }
               handler.delEnt(this);
           }

        }
        doCollision();

    }

    public void render(Graphics g) {

    }

    @Override
    public void renderTop(Graphics g) {
        g.setColor(Color.RED);
        if(explosionProgress == 0) {
            g.drawImage(preExplosion, (int) x, (int) y, null);
        } else {
            g.drawImage(image, (int) x - 8, (int) y - 8, null);
        }

    }

}
