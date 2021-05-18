package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Coord;
import sin.lib.Direction;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.save.ISaveable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EntityWormBoss extends Entity {
    private int counter;
    private Boolean slamming;
    private int spriteIndex;
    Polysprite ps;
    Polysprite eps;
    Polysprite sps;
    Polysprite attack;
    public BufferedImage rock;

    public int[] posx;
    public int[] posy;
    int pos = 0;
    boolean moving;
    int cycler;
    int moveTimer;

    int gunTicker;

    BufferedImage bar;
    BufferedImage bartop;

    public EntityWormBoss(float x, float y, Game game) {
        super(x, y, 32, 64, EntityType.Boss, game); // Kai said the worm would be 32x64 but not positive
        this.health = 2000;
        ps = new Polysprite("entities/wormBossIdle.png",8, 8, 32, 64); // Kai said there would be 9 images in the sprite sheet
        eps = new Polysprite("entities/WormBossEmerge.png",7, 8, 32, 64); // Kai said there would be 9 images in the sprite sheet
        sps = new Polysprite("entities/WormBossSubmerge.png",7, 8, 32, 64); // Kai said there would be 9 images in the sprite sheet
        attack = new Polysprite("entities/wormbossworking.png", 8, 4, 176, 160);
        image = ps.getCurImage(0);
        hb = new Rectangle((int) x, (int) y + 6, width, height - 6);
        slamming = false;
        rock = Lib.getImage("src/resources/entities/redRock.png");

        pos = 0;
        posx = new int[3];
        posy = new int[3];
        posx[0] = 480;
        posx[1] = 352;
        posx[2] = 608;

        posy[0] = 66;
        posy[1] = 242;
        posy[2] = 242;
        pos = 0;
    }
    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        extra.put("counter", counter);
        extra.put("spriteIndex", spriteIndex);
        return extra;
    }

    public void doMove() {
        Random random = new Random();
        int r = random.nextInt(2);
        pos = pos == 1 ? r == 0 ? 0 : 2 : pos == 2 ? r == 0 ? 0 : 1 : pos == 0 ? r == 0 ? 1 : 2 : 0;
        this.x = posx[pos];
        this.y = posy[pos];
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
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        Vector playerLoc = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), 30);
        float distance = toPlayer.getMagnitude();
        if(distance < 400) {
           game.hud.drawb1 = true;
           game.hud.b1health = (int) health;
        } else {
            game.hud.drawb1 = false;
        }
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
        if(distance > 80 && distance < 500) {
            if(gunTicker > 7) {
                EntityWormBullet proj = new EntityWormBullet(getXMid(), getYMid() - 20, game, rock);
                Vector vector = new Vector(getXMid(), getYMid() - 20, game.player.getXMid(), game.player.getYMid() - 20, 7);
                proj.setVelX(vector.getHorizComp());
                proj.setVelY(vector.getVertComp());
                handler.addEnt(proj);
                gunTicker = 0;
            }
            gunTicker++;
        }
        cycler = Lib.cycle(cycler, 0, 3);
        if(cycler == 2) {
            spriteIndex = Lib.cycle(spriteIndex, 0, 7);
        }
        image = ps.getCurImage(spriteIndex, getDirectionToPlayer(), Direction.S);
        doDamage();
        if (health <= 0) {
            // This is where a death animation would go I think
            handler.delEnt(this);
            game.hud.drawb1 = false;
        }
        Random random = new Random();
        if(random.nextInt(1000) > 980 && !moving) {
            moving = true;
            moveTimer = 0;
        }
        if(moving) {

            if(moveTimer < 7) {
                image = sps.getCurImage(moveTimer, getDirectionToPlayer(), Direction.S);
            } else if(moveTimer == 7) {
                doMove();
            } else if(moveTimer > 7 && moveTimer < 15) {
                image = eps.getCurImage(moveTimer - 8, getDirectionToPlayer(), Direction.S);
            } else {
                moving = false;
            }
            moveTimer++;
        }

    }

    public Direction getDirectionToPlayer() {
        Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        return vector.getDirection();
    }


    public void render(Graphics g) {
        if(image.getWidth() == 32) {
            g.drawImage(image.getSubimage(0, 32, 32, 32), (int) x, (int) y + 32, null);
        } else {

        }
    }

    public void renderTop(Graphics g) {
        if(image.getWidth() == 32) {
            g.drawImage(image.getSubimage(0, 0, 32, 32), (int) x, (int) y, null);
        } else {

        }
    }

}


