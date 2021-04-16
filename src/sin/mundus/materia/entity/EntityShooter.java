package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Vector;
import sin.save.ISaveable;

import java.awt.*;

public class EntityShooter extends Entity {

    int speed;
    int counter;

    public EntityShooter(float x, float y, int width, int height, int speed, EntityType type, Game game) {
        super(x, y, width, height, type, game);
        this.speed = speed;
        this.counter = 0;
    }

    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("counter", counter);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        counter = obj.getInt("counter");
        return this;
    }

    @Override
    public void tick() {
        if(counter > 20) {
            Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), speed);
            EntityProjectile proj = new EntityProjectile(getXMid(), getYMid(), 16, 16, EntityType.Enemy, game);
            proj.setVelX(vector.getHorizComp());
            proj.setVelY(vector.getVertComp());
            handler.addEnt(proj);
            counter = 0;
        } else {
            counter++;
        }
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        super.render(g);

    }

}
