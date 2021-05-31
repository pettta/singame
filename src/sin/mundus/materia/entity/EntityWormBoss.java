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
    Direction slamDirection;
    private int spriteIndex;
    private int slamDelay;
    Polysprite ps;
    Polysprite eps;
    Polysprite sps;
    Polysprite attack;
    public BufferedImage rock;

    float lastHealth;

    public int[] posx;
    public int[] posy;
    int pos = 0;
    boolean moving;
    int cycler;
    int moveTimer;
    int moveDelay;
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
        if(!slamming) {
            Random random = new Random();
            int r = random.nextInt(2);
            pos = pos == 1 ? r == 0 ? 0 : 2 : pos == 2 ? r == 0 ? 0 : 1 : pos == 0 ? r == 0 ? 1 : 2 : 0;
            this.x = posx[pos];
            this.y = posy[pos];
            moveDelay = 40;
        }
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
           game.audioPlayer.playAudio("DungeonTrack3.wav");
        } else {
            game.hud.drawb1 = false;
        }
        slamDelay--;
        moveDelay--;
        if ((distance < 60 || slamming) && slamDelay < 0 && !moving) {
            counter++;
            if(!slamming) {
                velX = playerLoc.getHorizComp();
                velY = playerLoc.getVertComp();
                slamDirection = Vector.getCardinalDirectionMirrored(velX, velY);
                image = attack.getCurImage(0, slamDirection, slamDirection);
                slamming = true;
            }
        }
        if(slamming && counter < 20) {
            image = attack.getCurImage(counter < 7 ? 0 : 1, slamDirection, slamDirection);
        }
        if (counter >= 20 && counter < 30){
            image = attack.getCurImage(counter < 21 ? 2 : 3, slamDirection, slamDirection);
            if(counter > 26 && counter < 35) {
                Rectangle slambox = null;
                if(slamDirection == Direction.W) {
                    slambox = new Rectangle((int) x + 16, (int) y + 32, 80, 24);
                }
                if(slamDirection == Direction.E) {
                    slambox = new Rectangle((int) x + 16 - 80, (int) y + 32, 80, 24);
                }
                if(slamDirection == Direction.N) {
                    slambox = new Rectangle((int) x, (int) y - 32, 32, 80);
                }
                if(slamDirection == Direction.S) {
                    slambox = new Rectangle((int) x, (int) y + 64, 32, 80);
                }
                if(slambox.intersects(game.player.getBounds())) {
                    game.player.health -= 10;
                }
            }
        }
        if(counter >= 30 && counter < 35) {
            image = attack.getCurImage(counter <= 31 ? 4 : counter <= 32 ? 5 : counter <= 33 ? 6 : 7, slamDirection, slamDirection);
        }
        if(counter > 40) {
            slamming = false;
            counter = 0;
            slamDelay = 40;
        }

        if(distance > 60 && distance < 500) {
            if(gunTicker > 7) {
                EntityWormBullet proj = new EntityWormBullet(getXMid(), getYMid() - 20, game, rock);
                Vector vector = new Vector(getXMid(), getYMid() - 20, game.player.getXMid(), game.player.getYMid(), 7);
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
        if(!slamming) {
            image = ps.getCurImage(spriteIndex, getDirectionToPlayer(), Direction.S);
        }
        doDamage();
        if (health <= 0) {
            // This is where a death animation would go I think
            handler.delEnt(this);
            game.hud.drawb1 = false;
            game.audioPlayer.playAudio("DungeonTrack1.wav");
        }
        Random random = new Random();
        if(random.nextInt(1000) > 980 && !moving && !slamming && moveDelay < 0) {
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
            g.drawImage(image, (int) x - 80, (int) y - 32, null);
        }
    }

}


