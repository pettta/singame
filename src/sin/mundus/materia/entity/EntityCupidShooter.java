package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Direction;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.mundus.materia.tile.Tile;
import sin.save.ISaveable;

import java.awt.*;

public class EntityCupidShooter extends Entity {
    private int counter;
    private int spriteIndex;
    Polysprite ps;
    Polysprite psa;
    Direction lastDirection;

    boolean vertCollision;
    boolean horizCollision;

    int updateCounter;

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
            if(ent.getType() == EntityType.Enemy || ent.getType() == EntityType.NPC || ent.getType() == EntityType.Chest || ent.getType() == EntityType.Rock) {
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
            if(ent.getType() == EntityType.Enemy || ent.getType() == EntityType.NPC || ent.getType() == EntityType.Chest || ent.getType() == EntityType.Rock) {
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

    public EntityCupidShooter(float x, float y, Game game) {
        super(x, y, 32, 22, EntityType.Enemy, game);
        this.speed = 10;
        this.health = 100;
        ps = new Polysprite("entities/Cupididle.png", 2, 4, 32, 32);
        psa = new Polysprite("entities/CupAttackSheet.png", 5, 4, 32, 32);
        image = ps.getCurImage(0);
        hb = new Rectangle((int) x, (int) y, width, height);
        lastDirection = Direction.S;
        velX = 0;
        velY = 0;
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
            if (ent.getType() == EntityType.Player) {
                if (getBounds().intersects(ent.getBounds())) {
                    ent.health -= 30;
                }
            }
        }
    }
    public void tick() {
        counter--;
        doCollision();
        updateCounter--;

        if(updateCounter < 0) {
            velX = game.player.velX * 2;
            velY = game.player.velY * 2;
            updateCounter = 5;
        }

        x += horizCollision ? 0 : velX;
        y += vertCollision ? 0 : velY;
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        float distance = toPlayer.getMagnitude();
        if(counter < 0 && distance < 125) {
            if(counter < -10) {
                EntityRangedShot proj = new EntityRangedShot(getXMid(), getYMid(), game, 0, EntityType.Player);
                Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), speed);
                proj.setVelX(vector.getHorizComp());
                proj.setVelY(vector.getVertComp());
                handler.addEnt(proj);
                counter = 60;
            } else {
                image = psa.getCurImage((counter * -1 - 1)/2, Vector.getCardinalDirection(toPlayer.getHorizComp(), toPlayer.getVertComp()));
            }
        }
        spriteIndex = Lib.cycle(spriteIndex, 0, 19);
        if(counter >= 0) {
            image = ps.getCurImage(spriteIndex / 10, Vector.getCardinalDirection(toPlayer.getHorizComp(), toPlayer.getVertComp()));
        }
        doDamage();
        if(health <= 0) {
            handler.delEnt(this);
        }
        horizCollision = false;
        vertCollision = false;
        //hb.x = (int) x;
        //hb.y = (int) y;
    }

    public void render(Graphics g) {

    }

    public void renderTop(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
}
