package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Lib;
import sin.mundus.materia.sprite.Polysprite;
import sin.mundus.materia.tile.Tile;
import sin.save.ISaveable;

import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * Name: EntityBomb.java
 * Purpose: A bomb that blows up, damages entities, and breaks rock entities.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class EntityBomb extends Entity {

    int life;
    int explosionProgress;
    Polysprite ps;
    BufferedImage image;
    BufferedImage preExplosion;

    public EntityBomb(float x, float y, Game game) {
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
            if(ent.getType() == EntityType.Rock && ent.getBounds().intersects(getBounds())) {
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
                       if(ent.type == EntityType.Rock) {
                           ent.onInteract(4);
                       }
                   }
               }
               handler.delEnt(this);
           }

        }
        doCollision();

    }

    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("life", life);
        extra.put("explosionProgress", explosionProgress);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        life = obj.getInt("life");
        explosionProgress = obj.getInt("explosionProgress");
        return this;
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
