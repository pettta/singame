package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.display.HUD;
import sin.item.ItemMelee;
import sin.item.ItemRanged;
import sin.item.ItemSpecial;
import sin.lib.Direction;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.mundus.materia.tile.Tile;
import sin.mundus.map.Teleporter;
import sin.save.ISaveable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * Name: EntityNPC.java
 * Purpose: Renders and ticks an entity that can talk to you.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class EntityNPC extends Entity {

    int spriteIndex, indexCounter;
    int counter;
    public Direction lastDirection;
    Polysprite ps;
    int busyticker;


    boolean notbusy;

    boolean horizCollision;
    boolean vertCollision;

    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("spriteIndex", spriteIndex);
        extra.put("indexCounter", indexCounter);
        extra.put("lastDirection", lastDirection.value);
        return extra;
    }


    public void onInteract(int id) {
        if(id == 0) {
            if (!game.dialogue.dialogue) {
                notbusy = false;
                game.dialogue.talk(this, "Whatever you do, don't go into the hole down south!! That'd be terrible!! Please don't do that!");
                velX = 0;
                velY = 0;
                Direction dir = Vector.getCardinalDirection(game.player.getXMid() - getXMid(), game.player.getYMid() - getYMid());
                lastDirection = dir == Direction.W ? Direction.E : dir == Direction.E ? Direction.W : dir;
                updateImage();
                game.player.freeze();
            }
        } else {
            notbusy = true;
            game.player.unfreeze();
        }
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        spriteIndex = obj.getInt("spriteIndex");
        indexCounter = obj.getInt("indexCounter");
        lastDirection = Direction.from(obj.getInt("lastDirection"));
        return this;
    }

    public EntityNPC(float x, float y, float speed, Game game) {
        super(x, y, 16, 32, EntityType.NPC, game);
        this.speed = speed;
        this.lastDirection = Direction.S;
        this.ps = new Polysprite("entities/oldMan.png",4,8, width, height);
        this.hb = new Rectangle((int)x , (int)y + 16, 16, 16);
        updateImage();
        health = 100;
        notbusy = true;
    }

    public void updatePos() {
        hb.x = (int) x;
        hb.y = (int) y + 16;
    }

    // Returns number between 0-9: int nxt = ran.nextInt(10);
    public void randomMovement() {
        if(notbusy) {
            counter++;
            if (counter >= 20) {
                Random r = new Random();
                int r1 = r.nextInt(2) == 0 ? 1 : -1;
                int r2 = r.nextInt(2);
                if (r2 == 0) {
                    velX = r1 * speed;
                    velY = 0;
                } else {
                    velY = r1 * speed;
                    velX = 0;
                }
                updateImage();
                counter = 0;
            }
        }
    }

    public void tick() {
        doCollision();
        randomMovement();
        x += horizCollision ? 0 : velX;
        y += vertCollision ? 0 : velY;
        updatePos();
        indexCounter++;
        if(indexCounter >= 6) {
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
            updateImage();

            indexCounter = 0;
        }
        horizCollision = false;
        vertCollision = false;
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
            if(ent.getType() != EntityType.NPC) {
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
            if(ent.getType() != EntityType.NPC) {
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
        g.setColor(Color.RED);
    }

}
