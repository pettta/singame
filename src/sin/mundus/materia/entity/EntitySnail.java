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
import java.awt.image.BufferedImage;

public class EntitySnail extends Entity {

    private int counter;
    private int spriteIndex;
    Polysprite ps;

    Direction lastDirection;

    int damageCounter;

    boolean vertCollision;
    boolean horizCollision;

    public int bloodCounter;

    int updateCounter;
    BufferedImage blood;

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


    }

    public EntitySnail(float x, float y, Game game) {
        super(x, y, 32, 32, EntityType.Enemy, game);
        this.speed = 10;
        this.health = 100;
        ps = new Polysprite("entities/SnailEnemy.png", 4, 4, 32, 32);
        image = ps.getCurImage(0);
        hb = new Rectangle((int) x, (int) y, width, height);
        blood = Lib.getImage("src/resources/entities/blood.png");
        lastDirection = Direction.S;
        velX = 0;
        velY = 0;
    }

    public void damaged() {
        bloodCounter = 6;

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
                    if(damageCounter < 0) {
                        ent.health -= 30;
                        damageCounter = 15;
                    }
                }
            }
        }
    }
    public void tick() {
        counter--;
        doCollision();
        updateCounter--;
        damageCounter--;
        bloodCounter--;

        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        float distance = toPlayer.getMagnitude();

        if(updateCounter < 0) {
            if (distance < 1000){
                lastDirection = Vector.getCardinalDirection(toPlayer.getHorizComp(), toPlayer.getVertComp());
                if(lastDirection == Direction.N) {
                    velY = -2;
                    velX = 0;
                }
                if(lastDirection == Direction.E) {
                    velY = 0;
                    velX = 2;
                }
                if(lastDirection == Direction.S) {
                    velY = 2;
                    velX = 0;
                }
                if(lastDirection == Direction.W) {
                    velY = 0;
                    velX = -2;
                }
            }
            updateCounter = 20;


        }

        x += horizCollision ? 0 : velX;
        y += vertCollision ? 0 : velY;

        spriteIndex = Lib.cycle(spriteIndex, 0, 31);
        image = ps.getCurImage(spriteIndex / 8, lastDirection);

        doDamage();
        if(health <= 0) {
            handler.delEnt(this);
            game.player.schmoney += 3;
        }
        horizCollision = false;
        vertCollision = false;
    }

    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
        if(bloodCounter > 0) {

            g.drawImage(blood.getSubimage(0, 0, width, height), (int) x, (int) y, null);
        }
    }

    public void renderTop(Graphics g) {

    }
}
