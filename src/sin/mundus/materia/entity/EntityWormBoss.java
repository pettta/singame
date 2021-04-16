package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.save.ISaveable;

import java.awt.*;

public class EntityWormBoss extends Entity {
    private int counter;
    private Boolean slamming;
    private int spriteIndex;
    Polysprite ps;

    public EntityWormBoss(float x, float y, Game game) {
        super(x, y, 16, 22, EntityType.Enemy, game); // Kai said the worm would be 32x64 but not positive
        this.health = 200;
        ps = new Polysprite("entities/worm.png",15, 1, width, height); // Kai said there would be 9 images in the sprite sheet
        image = ps.getCurImage(0);
        hb = new Rectangle((int) x, (int) y + 6, width, height - 6);
        slamming = false;
    }
    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("counter", counter);
        extra.put("spriteIndex", spriteIndex);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        counter = obj.getInt("counter");
        spriteIndex = obj.getInt("spriteIndex");
        return this;
    }
    private void doDamage() {
        for (int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if (ent.getType() == EntityType.Rock) {
                if (getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(ent);
                    // Add line for damage animation here i think?
                    health -= 34;
                }
            }
        }
    }

    public void tick() {
        System.out.println(counter);
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        Vector playerLoc = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), 30);
        float distance = toPlayer.getMagnitude();
        if (distance < 60 || slamming) {
            counter++;
            slamming = true;
        }
        if (counter == 30){
            velX = playerLoc.getHorizComp();
            velY = playerLoc.getVertComp();
            x += velX;
            y += velY;
            hb.x = (int) x;
            hb.y = (int) y;
        }
        else if ( counter == 50) {
            velX *= -1;
            velY *= -1;
            x += velX;
            y += velY;
            hb.x = (int) x;
            hb.y = (int) y;
        }
        else if (counter > 70) {
            velX = 0;
            velY = 0;
            counter = 0;
            slamming = false;
        }
        else if(distance > 80 && distance < 120) {
            EntityWormBullet proj = new EntityWormBullet(getXMid(), getYMid(), game, ps.getCurImage(14));
            Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), 2);
            proj.setVelX(vector.getHorizComp());
            proj.setVelY(vector.getVertComp());
            handler.addEnt(proj);
        }
        spriteIndex = Lib.cycle(spriteIndex, 0, 13);
        image = ps.getCurImage(spriteIndex);
        doDamage();
        if (health <= 0) {
            // This is where a death animation would go I think
            handler.delEnt(this);
        }

    }
    public void render(Graphics g) {
        g.drawImage(image.getSubimage(0, 6, 16, 16), (int) x, (int) y + 6, null);
    }

    public void renderTop(Graphics g) {
        g.drawImage(image.getSubimage(0, 0, 16, 6), (int) x, (int) y, null);
    }

}


