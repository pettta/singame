package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Coord;
import sin.lib.Direction;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.save.ISaveable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EntityGoat extends Entity {

    int spriteIndex;
    int cycler;

    Polysprite idle;
    Polysprite walk;
    Polysprite attack;

    int state;

    public enum State {
        Idle,
        Walk,
        Attack
    }

    public EntityGoat(float x, float y, Game game) {
        super(x, y, 32, 64, EntityType.Boss, game);

        this.health = 3000;
        idle = new Polysprite("entities/GoatBossIdle.png",2, 8, 64, 80);
        walk = new Polysprite("entities/GoatBossWalk.png",4, 8, 64, 80);
        walk = new Polysprite("entities/GoatAttack.png",5, 4, 96, 176);

        image = idle.getCurImage(0);
        hb = new Rectangle((int) x, (int) y, width, height);

    }
    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("spriteIndex", spriteIndex);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        spriteIndex = obj.getInt("spriteIndex");
        return this;
    }
    private void doDamage() {
        for (int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if (ent.getType() == EntityType.Rock) {
                if (getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(ent);
                    health -= 34;
                }
            }
        }
    }

    public void tick() {
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        float distance = toPlayer.getMagnitude();

        cycler = Lib.cycle(cycler, 0, 2);
        if(cycler == 2) {
            spriteIndex = Lib.cycle(spriteIndex, 0, 1);
            image = idle.getCurImage(spriteIndex, toPlayer.getDirection());
        }

        doDamage();
        if (health <= 0) {
            handler.delEnt(this);

        }

    }

    public Direction getDirectionToPlayer() {
        Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        return vector.getDirection();
    }


    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

    public void renderTop(Graphics g) {

    }

}


