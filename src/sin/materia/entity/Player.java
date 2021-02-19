package sin.materia.entity;

import sin.Game;
import sin.display.HUD;
import sin.lib.Direction;
import sin.lib.EntityType;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.materia.sprite.Polysprite;

public class Player extends Entity {

    int invulnCounter, spriteIndex, indexCounter;
    Direction lastDirection;
    Polysprite ps;

    public Player(float x, float y, float speed, Game game) {
        super(x, y, 16, 32, EntityType.Player, game);
        this.speed = speed;
        this.lastDirection = Direction.S;
        this.ps = new Polysprite("entities/player.png",4,8, width, height);
        this.image = ps.getCurImage(0, lastDirection, lastDirection);
    }

    public void tick() {
        super.tick();
        indexCounter++;
        if(indexCounter >= 4) {
            spriteIndex = Lib.cycle(spriteIndex, 0, 3);
            image = ps.getCurImage(spriteIndex, getRoughDirection(), lastDirection);
            indexCounter = 0;
        }
        intersects();
    }

    private boolean intersects() {
        invulnCounter--;
        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if(ent.getType() == EntityType.Projectile || ent.getType() == EntityType.Enemy) {
                if(getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(ent);
                    if(invulnCounter <= 0) {
                        HUD.health -= 20;
                        invulnCounter += 20;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void updateImage() {
        image = ps.getCurImage(spriteIndex, getRoughDirection(), lastDirection);
    }

    public void updateLastDirection() {
        setLastDirection(getRoughDirection());
    }

    public void setLastDirection(Direction direction) {
        lastDirection = direction;
    }

    public Direction getRoughDirection() {
        return Vector.getRoughDirection(velX, velY);
    }

}
