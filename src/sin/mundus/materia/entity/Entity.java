package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.Handler;
import sin.lib.Lib;
import sin.mundus.map.Map;
import sin.mundus.materia.Materia;
import sin.save.ISaveable;

public class Entity extends Materia implements ISaveable {

    protected Handler handler;
    protected Game game;
    protected EntityType type;
    float speed, velX, velY;
    public float health;

    public Entity(float x, float y, int width, int height, EntityType type, Game game) {
        super(x, y, width, height);
        this.type = type;
        this.game = game;
        this.handler = game.getHandler();
    }

    // 1: generic start
    // 2: generic end
    // rest: dependent on entity
    public void onInteract(int num) {

    }

    public void damaged() {

    }

    public void tick() {
        x += velX;
        y += velY;
        hb.x = (int) x;
        hb.y = (int) y;
    }

    public JSONObject write(JSONObject obj) {
        obj.put("x", x);
        obj.put("y", y);
        obj.put("velX", velX);
        obj.put("velY", velY);
        obj.put("health", health);
        return obj;
    }

    public ISaveable read(JSONObject obj) {
        x = obj.getFloat("x");
        y = obj.getFloat("y");
        velX = obj.getFloat("velX");
        velY = obj.getFloat("velY");
        health = obj.getFloat("health");
        return this;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public boolean isMoving() {
        return (velX != 0 || velY != 0);
    }

    public EntityType getType() {
        return type;
    }

    public float getSpeed() {
        return speed;
    }

    public float getHealth() {
        return health;
    }

}
