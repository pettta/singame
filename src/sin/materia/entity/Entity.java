package sin.materia.entity;

import sin.Game;
import sin.Handler;
import sin.lib.EntityType;
import sin.materia.Materia;

public class Entity extends Materia {

    protected Handler handler;
    protected Game game;
    protected EntityType type;
    float speed, velX, velY;
    int health;

    public Entity(float x, float y, int width, int height, EntityType type, Game game) {
        super(x, y, width, height);
        this.type = type;
        this.game = game;
        this.handler = game.getHandler();
    }

    public void tick() {
        x += velX;
        y += velY;
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

    public int getHealth() {
        return health;
    }

}
