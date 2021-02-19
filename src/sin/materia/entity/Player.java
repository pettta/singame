package sin.materia.entity;

import sin.Game;
import sin.display.HUD;
import sin.lib.Direction;
import sin.lib.EntityType;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.materia.sprite.Polysprite;
import sin.materia.tile.Tile;

import java.awt.*;

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
        //doCollision();
    }

    public Rectangle getHorizCollision() {

        float bx = x + velX;
        float by = y + (height / 2);
        float bw = width + velX / 2;
        float bh = height / 2;

        return new Rectangle((int) bx, (int) by, (int)bw, (int)bh);

    }

    public Rectangle getVertCollision() {
        float bx = x;
        float by = y + (height / 2) + velY;
        float bw = width;
        float bh = height / 2 + velY / 2;

        return new Rectangle((int) bx, (int) by, (int)bw, (int)bh);
    }


    public Rectangle getCollisionBounds() {
        return new Rectangle((int) x, (int) (y + (height / 2)), width, height / 2);
    }


    private boolean doCollision() {
        for(int i = 0; i < game.map.getTiles().size(); i++) {
            Tile tile = game.map.getTiles().get(i);
            if(tile.isCollides()) {
                if(getHorizCollision().intersects(tile.getBounds())) {
                    if(velX > 0) {
                        // Into left of object.
                        velX = 0;
                        x = tile.getX() - width;
                    } else {
                        // Into right of object.
                        velX = 0;
                        x = tile.getX() + tile.getWidth();
                    }
                    velY = 0;
                }
                if(getVertCollision().intersects(tile.getBounds())) {
                    if(velY > 0) {
                        // Into top of object.
                        velX = 0;
                        y = tile.getY() - height;
                    } else {
                        // Into bottom of object.
                        velY = 0;
                        y = tile.getY() + tile.getHeight() - height / 2;
                    }
                    velY = 0;
                }

            }
        }
        return false;
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
