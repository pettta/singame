package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.Registry;
import sin.item.Stack;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.save.ISaveable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityWormShooter extends Entity {

    private int shootCounter;
    private int spriteIndex;
    Polysprite ps;

    public int bloodCounter;

    BufferedImage blood;

    public EntityWormShooter(float x, float y, Game game) {
        super(x, y, 16, 22, EntityType.Enemy, game);
        this.speed = 10;
        this.health = 100;
        ps = new Polysprite("entities/worm.png",15, 1, width, height);
        blood = Lib.getImage("src/resources/entities/blood.png");
        image = ps.getCurImage(0);
        hb = new Rectangle((int) x, (int) y + 6, width, height - 6);
    }
    // TODO is saving sprite index really necesssary? eh sure why not
    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("shootCounter", shootCounter);
        extra.put("spriteIndex", spriteIndex);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        shootCounter = obj.getInt("shootCounter");
        spriteIndex = obj.getInt("spriteIndex");
        return this;
    }

    private void doDamage() {
        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if(ent.getType() == EntityType.Rock) {
                if(getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(ent);
                    health -= 34;
                }
            }
        }
    }

    public void damaged() {
        bloodCounter = 6;

    }

    public void tick() {
        shootCounter++;
        bloodCounter--;
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        float distance = toPlayer.getMagnitude();
        if(shootCounter >= 30 && distance < 125) {
            EntityWormBullet proj = new EntityWormBullet(getXMid(), getYMid(), game, ps.getCurImage(14));
            Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), speed);
            proj.setVelX(vector.getHorizComp());
            proj.setVelY(vector.getVertComp());
            handler.addEnt(proj);
            shootCounter = 0;
        }
        spriteIndex = Lib.cycle(spriteIndex, 0, 13);
        image = ps.getCurImage(spriteIndex);
        doDamage();
        if(health <= 0) {
            handler.delEnt(this);
            game.player.schmoney += 3;
            EntityStack stack2 = new EntityStack(x, y, game, new Stack(Registry.wormHide, 1));
            handler.addEnt(stack2);
            game.player.setWrath(game.player.getWrath() + 5);
        }
    }

    public void render(Graphics g) {
        g.drawImage(image.getSubimage(0, 6, 16, 16), (int) x, (int) y + 6, null);
    }

    public void renderTop(Graphics g) {
        g.drawImage(image.getSubimage(0, 0, 16, 6), (int) x, (int) y, null);
        if(bloodCounter > 0) {

            g.drawImage(blood.getSubimage(0, 0, width, height), (int) x, (int) y, null);
        }
    }

}
