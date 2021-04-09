package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Lib;
import sin.mundus.materia.tile.Tile;
import sin.save.ISaveable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityRangedShot extends Entity {

    int life;
    BufferedImage img;

    public EntityRangedShot(float x, float y, Game game) {
        super(x, y, 8, 8, EntityType.Rock, game);
        int life = 0;
        this.img = Lib.getImage("src/resources/entities/rock.png");
        this.speed = 10;
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
        for(int i = 0; i < game.map.getTiles().size(); i++) {
            Tile tile = game.map.getTiles().get(i);
            if(tile.isCollides() && getBounds().intersects(tile.hb)) {
                handler.delEnt(this);
            }
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
