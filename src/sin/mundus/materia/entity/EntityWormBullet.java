package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.save.ISaveable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityWormBullet extends Entity {

    int life;
    BufferedImage img;

    public EntityWormBullet(float x, float y, Game game, BufferedImage img) {
        super(x, y, 8, 8, EntityType.Projectile, game);
        int life = 0;
        this.img = img;
        this.speed = 5;
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
        Graphics2D g2d = (Graphics2D) g;

        g.drawImage(img, (int) x, (int) y, null);
    }

}
