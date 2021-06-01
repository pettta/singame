package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.save.ISaveable;

import java.awt.*;
/**
 * Name: EntityProjectile.java
 * Purpose: Manages a projectile that shoots and deals damage.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class EntityProjectile extends Entity {

    int life;


    public EntityProjectile(float x, float y, int width, int height, EntityType type, Game game) {
        super(x, y, width, height, type, game);
        int life = 0;


    }

    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("life", life);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        life = obj.getInt("life");
        return this;
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
