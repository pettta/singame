package sin.mundus.materia.entity;

import sin.Game;
import sin.display.HUD;
import sin.lib.Direction;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.mundus.materia.tile.Tile;
import sin.mundus.map.Teleporter;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Entity {

    int invulnCounter, spriteIndex, indexCounter;
    Direction lastDirection;
    Polysprite ps;

    boolean horizCollision;
    boolean vertCollision;

    public float[] sins;

    public void doDeath() {

    }

    public Player(float x, float y, float speed, Game game) {
        super(x, y, 16, 32, EntityType.Player, game);
        this.speed = speed;
        this.lastDirection = Direction.S;
        this.ps = new Polysprite("entities/player.png",4,8, width, height);
        this.image = ps.getCurImage(0, lastDirection, lastDirection);
        this.hb = new Rectangle((int)x , (int)y + 16, 16, 16);
        sins = new float[7];
    }

    public void updatePos() {
        hb.x = (int) x;
        hb.y = (int) y + 16;
    }

    public void tick() {
        doCollision();
        x += horizCollision ? 0 : velX;
        y += vertCollision ? 0 : velY;
        updatePos();
        indexCounter++;
        if(indexCounter >= 4) {
            spriteIndex = Lib.cycle(spriteIndex, 0, 3);
            Direction dir = getRoughDirection();
            if(dir == Direction.N || dir == Direction.S || dir == Direction.E || dir == Direction.W) {
                if(horizCollision || vertCollision) {
                    spriteIndex = 0;
                }
            } else {
                if (horizCollision && vertCollision) {
                    spriteIndex = 0;
                }
            }
            image = ps.getCurImage(spriteIndex, getRoughDirection(), lastDirection);
            indexCounter = 0;
        }
        horizCollision = false;
        vertCollision = false;
        doDamage();
        doTeleport();
        sins[0] = Lib.clamp(sins[0], 0, 45);
        sins[1] = Lib.clamp(sins[1], 0, 45);
        sins[2] = Lib.clamp(sins[2], 0, 45);
        sins[3] = Lib.clamp(sins[3], 0, 45);
        sins[4] = Lib.clamp(sins[4], 0, 45);
        sins[5] = Lib.clamp(sins[5], 0, 45);
        sins[6] = Lib.clamp(sins[6], 0, 45);


    }

    public void doCollision() {
        // HORIZONTAL COLLISION
        hb.x += velX;
        // Tiles

        for(int i = 0; i < game.map.getTiles().size(); i++) {
            Tile tile = game.map.getTiles().get(i);
            if(tile.isCollides() && hb.intersects(tile.hb)) {
                hb.x -= velX;
                while(!hb.intersects(tile.hb)) {
                    hb.x += Math.signum(velX);
                }
                hb.x -= Math.signum(velX);
                horizCollision = true;
                x = hb.x;

            }
        }


        // Entities
        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if(ent.getType() == EntityType.Enemy) {
                if(hb.intersects(ent.hb)) {
                    hb.x -= velX;
                    while(!hb.intersects(ent.hb)) {
                        hb.x += Math.signum(velX);
                    }
                    hb.x -= Math.signum(velX);
                    horizCollision = true;
                    x = hb.x;
                }
            }
        }
        // VERTICAL COLLISION
        hb.y += velY;
        // Tiles

        for(int i = 0; i < game.map.getTiles().size(); i++) {
            Tile tile = game.map.getTiles().get(i);
            if(tile.isCollides() && hb.intersects(tile.hb)) {
                hb.y -= velY;
                while(!hb.intersects(tile.hb)) {
                    hb.y += Math.signum(velY);
                }
                hb.y -= Math.signum(velY);
                vertCollision = true;
                y = hb.y - 16;
            }
        }

        // Entities
        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if(ent.getType() == EntityType.Enemy) {
                if(hb.intersects(ent.hb)) {
                    hb.y -= velY;
                    while(!hb.intersects(ent.hb)) {
                        hb.y += Math.signum(velY);
                    }
                    hb.y -= Math.signum(velY);
                    vertCollision = true;
                    y = hb.y - 16;
                }
            }
        }

    }


    private void doDamage() {
        invulnCounter--;
        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if(ent.getType() == EntityType.Projectile) {
                if(getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(ent);
                    if(invulnCounter <= 0) {
                        HUD.health -= 20;
                        invulnCounter += 20;
                    }
                }
            }
        }
    }

    public void doTeleport() {
        ArrayList<Teleporter> teleList = game.map.getTeleporters();
        for(int i = 0; i < teleList.size(); i++) {
            Teleporter t = teleList.get(i);
            if(hb.intersects(t.getBounds())) {
                t.doTeleport();
            }
        }
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

    // TODO Change how this is done perhaps to save more memory and for ease of use.
    public void render(Graphics g) {
        g.drawImage(image.getSubimage(0, 16, 16, 16), (int) x, (int) y + 16, null);
    }

    public void renderTop(Graphics g) {
        g.drawImage(image.getSubimage(0, 0, 16, 16), (int) x, (int) y, null);
    }


    public float getPride() {
        return sins[0];
    }
    public float getGreed() {
        return sins[1];
    }
    public float getLust() {
        return sins[2];
    }
    public float getEnvy() {
        return sins[3];
    }
    public float getGuilt() {
        return sins[4];
    }
    public float getWrath() {
        return sins[5];
    }
    public float getSloth() {
        return sins[6];
    }
    public void setPride(float val) {
        sins[0] = val;
    }
    public void setGreed(float val) {
        sins[1] = val;
    }
    public void setLust(float val) {
        sins[2] = val;
    }
    public void setEnvy(float val) {
        sins[3] = val;
    }
    public void setGuilt(float val) {
        sins[4] = val;
    }
    public void setWrath(float val) {
        sins[5] = val;
    }
    public void setSloth(float val) {
        sins[6] = val;
    }


}
