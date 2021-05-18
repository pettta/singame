package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.display.HUD;
import sin.display.Menu;
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

public class EntityPlayer extends Entity {

    int invulnCounter, spriteIndex, indexCounter;
    public Direction lastDirection;
    Polysprite ps;
    Polysprite psd;
    Polysprite psda;
    Polysprite psa;
    Polysprite psattack;

    boolean horizCollision;
    boolean vertCollision;

    public float maxHealth;

    boolean frozen;
    Direction frozendir;

    public float[] sins;
    boolean isAttacking;
    int attackCounter;

    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("invulnCounter", invulnCounter);
        extra.put("spriteIndex", spriteIndex);
        extra.put("indexCounter", indexCounter);
        extra.put("lastDirection", lastDirection.value);
        return extra;
    }

    public void freeze() {
        frozen = true;
        Direction rd = getRoughDirection();
        frozendir = rd != Direction.None ? rd : lastDirection != Direction.None ? lastDirection : Direction.S;

    }

    public void unfreeze() {
        frozen = false;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        invulnCounter = obj.getInt("invulnCounter");
        spriteIndex = obj.getInt("spriteIndex");
        indexCounter = obj.getInt("indexCounter");
        lastDirection = Direction.from(obj.getInt("lastDirection"));
        return this;
    }

    public void doDeath() {
        health = maxHealth;
        //game.gameState = Game.State.Menu;
        //game.menu.state = Menu.MenuState.Main;
    }

    public void doInteract() {
        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);

            Direction rd = getRoughDirection();
            Direction dir = rd != Direction.None ? rd : lastDirection != Direction.None ? lastDirection : Direction.S;
            Rectangle thb = getBounds();
            thb.x += 8 * Vector.xSignumFromDirection(dir);
            thb.y += 8 * Vector.ySignumFromDirection(dir);
            if(thb.intersects(ent.getBounds())) {
                ent.onInteract(0);
            }

        }
    }

    // TODO change how attacks work such that call a function from the weapon in the players hand, more universal
    public void meleeAttack() {
        if(!isAttacking) {
            if (game.inventory.getMeleeSlot().stack != null && game.inventory.getMeleeSlot().stack.item instanceof ItemMelee) {
                ((ItemMelee) game.inventory.getMeleeSlot().stack.item).onUse(game);
            }
            isAttacking = true;
            attackCounter = 0;
        }

    }

    public void specialAttack() {
        if(game.inventory.getSpecialSlot().stack != null && game.inventory.getSpecialSlot().stack.item instanceof ItemSpecial) {
            ((ItemSpecial) game.inventory.getSpecialSlot().stack.item).onUse(game);
        }
    }

    public void rangedAttack(int x, int y) {
        if(game.inventory.getRangedSlot().stack != null && game.inventory.getRangedSlot().stack.item instanceof ItemRanged) {
            ((ItemRanged) game.inventory.getRangedSlot().stack.item).onUse(game, x, y);
        }
    }

    public EntityPlayer(float x, float y, float speed, Game game) {
        super(x, y, 16, 32, EntityType.Player, game);
        this.speed = speed;
        this.lastDirection = Direction.S;
        this.ps = new Polysprite("entities/player.png",4,8, width, height);
        this.psd = new Polysprite("entities/playerDagger.png",4,8, width, height);
        this.psda = new Polysprite("entities/playerShadeCrystalDagger.png",4,8, width, height);
        this.psa = new Polysprite("entities/playerShadeCrystal.png",4,8, width, height);
        this.psattack = new Polysprite("entities/PlayerAttack.png", 4, 8, 48, 32);
        this.hb = new Rectangle((int)x , (int)y + 16, 16, 16);
        updateImage();
        sins = new float[7];
        maxHealth = 100;
        health = 100;
        frozendir = Direction.S;
    }

    public void updatePos() {
        hb.x = (int) x;
        hb.y = (int) y + 16;
    }

    public void tick() {

        doCollision();
        if(frozen) {
            velX = 0;
            velY = 0;
        }
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
            updateImage();
            indexCounter = 0;
        }
        if(isAttacking) {
            image = psattack.getCurImage(attackCounter, getRoughDirection(), lastDirection, true);
            attackCounter++;
            if(attackCounter == 4) {
                isAttacking = false;
                attackCounter = 0;
            }
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
        health = Lib.clamp(health, 0, 200);
        if(health == 0) {
            doDeath();
        }

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
            if(ent.getType() == EntityType.Enemy || ent.getType() == EntityType.NPC || ent.getType() == EntityType.Chest) {
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
            if(ent.getType() == EntityType.Enemy || ent.getType() == EntityType.NPC || ent.getType() == EntityType.Chest) {
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
                        health -= 20;
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
        Polysprite cur = ps;
        if(game.inventory.getMeleeSlot().stack == null && game.inventory.getArmorSlot().stack == null) {
            cur = ps;
        }
        if(game.inventory.getMeleeSlot().stack != null && game.inventory.getArmorSlot().stack == null) {
            cur = psd;
        }
        if(game.inventory.getMeleeSlot().stack == null && game.inventory.getArmorSlot().stack != null) {
            cur = psa;
        }
        if (game.inventory.getMeleeSlot().stack != null && game.inventory.getArmorSlot().stack != null) {
            cur = psda;
        }
        image = cur.getCurImage(frozen ? 0 : spriteIndex, frozen ? frozendir : getRoughDirection(), frozen ? frozendir : lastDirection );
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
        if(image.getWidth() == 16) {
            g.drawImage(image.getSubimage(0, 16, 16, 16), (int) x, (int) y + 16, null);
        } else {
            g.drawImage(image.getSubimage(0, 16, 48, 16), (int) x - 16, (int) y + 16, null);
        }
    }

    public void renderTop(Graphics g) {
        if(image.getWidth() == 16) {
            g.drawImage(image.getSubimage(0, 0, 16, 16), (int) x, (int) y, null);
        } else {
            g.drawImage(image.getSubimage(0, 0, 48, 16), (int) x - 16, (int) y, null);
        }

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
