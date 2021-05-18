package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.save.ISaveable;

import java.awt.*;

public class EntityCupidShooter extends Entity {
    private int counter;
    private int spriteIndex;
    Polysprite ps;

    public EntityCupidShooter(float x, float y, Game game) {
        super(x, y, 32, 22, EntityType.Enemy, game);
        this.speed = 10;
        this.health = 100;
        ps = new Polysprite("entities/Cupididle.png", 2, 4, 32, 32);
        image = ps.getCurImage(0);
        hb = new Rectangle((int) x, (int) y + 6, width, height - 6);
    }

    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("shootCounter", counter);
        extra.put("spriteIndex", spriteIndex);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        counter = obj.getInt("shootCounter");
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
        counter++;
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        float distance = toPlayer.getMagnitude();
        if(counter >= 60 && distance < 125) {
            EntityWormBullet proj = new EntityWormBullet(getXMid(), getYMid(), game, ps.getCurImage(14));
            Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), speed);
            proj.setVelX(vector.getHorizComp());
            proj.setVelY(vector.getVertComp());
            handler.addEnt(proj);
            counter = 0;
        }
        spriteIndex = Lib.cycle(spriteIndex, 0, 13);
        image = ps.getCurImage(spriteIndex);
        doDamage();
        if(health <= 0) {
            handler.delEnt(this);
        }
    }
}
