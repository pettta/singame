package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.tile.Tile;
import sin.save.ISaveable;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class EntityRangedShot extends Entity {

    int life;
    BufferedImage img;
    Vector vector;
    int projtype;
    EntityType[] damaged;

    // 0 = arrow
    // 1 = ball
    public EntityRangedShot(float x, float y, Game game, int projtype, EntityType... damaged) {
        super(x, y, 8, projtype == 0 ? 8 : 3, EntityType.Shot, game);
        int life = 0;
        this.damaged = damaged;
        this.projtype = projtype;
        this.img = projtype == 0 ? Lib.getImage("src/resources/entities/ArrowProjectile.png") : Lib.getImage("src/resources/entities/rock.png");
        this.speed = 10;
    }

    public EntityRangedShot(float x, float y, Game game, int projtype) {
        this(x, y, game, projtype, EntityType.Enemy, EntityType.Boss);
    }

    private void doDamage() {

        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            boolean entb = false;
            for(EntityType type : damaged) {
                if(type == ent.type) entb = true;
            }
            if(entb) {
                System.out.println("contains");
                if(getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(this);
                    ent.health -= 30;
                    ent.damaged();
                }
            }
        }
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
        if(vector == null) {
            vector = new Vector(velX, velY, true, false);
        }
        doDamage();
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
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        if(projtype == 0) {
            g2d.rotate(vector != null ? vector.getAngle() : 0, getXMid(), getYMid());
        }
        g.drawImage(img, (int) x, (int) y, null);
        if(projtype == 0) {
            g2d.setTransform(old);
        }
    }

    @Override
    public void renderTop(Graphics g) {

    }
}
