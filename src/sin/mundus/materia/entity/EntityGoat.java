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

public class EntityGoat extends Entity {

    int idleIndex;
    int walkIndex;
    int attackIndex;

    boolean drawBar;

    int attackTimer;

    float lastHealth;

    Polysprite idle;
    Polysprite walk;
    Polysprite attack;

    State state;

    int xo;
    int yo;

    BufferedImage rock;

    public enum State {
        Idle,
        Walk,
        Attack
    }

    public EntityGoat(float x, float y, Game game) {
        super(x, y, 32, 64, EntityType.Boss, game);

        this.health = 3000;
        this.lastHealth = 3000;
        idle = new Polysprite("entities/GoatBossIdle.png",2, 8, 64, 80);
        walk = new Polysprite("entities/GoatBossWalk.png",4, 8, 64, 80);
        attack = new Polysprite("entities/GoatAttack.png",5, 4, 96, 176);

        rock = Lib.getImage("src/resources/entities/redRock.png");
        image = idle.getCurImage(0);
        hb = new Rectangle((int) x, (int) y, width, height);
        state = State.Idle;

    }
    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        return this;
    }
    private void doDamage() {
        for (int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if (ent.getType() == EntityType.Rock) {
                if (getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(ent);
                    health -= 34;
                }
            }
            if (ent.getType() == EntityType.Player && state != State.Idle) {
                if (getBounds().intersects(ent.getBounds())) {
                    ent.health -= 5;
                }
            }
        }
    }

    public void tick() {
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        float distance = toPlayer.getMagnitude();
        Direction dirp = toPlayer.getDirection();
        if(distance > 100 && state == State.Walk) {
            state = State.Attack;
            velX = 0;
            velY = 0;
        }
        if(state == State.Idle) {
            velX = 0;
            velY = 0;
            idleIndex = Lib.cycle(idleIndex, 0, 15);
            xo = 0;
            yo = 0;
            image = idle.getCurImage(idleIndex / 8, dirp, Direction.S);
        } else if(state == State.Walk) {
            walkIndex = Lib.cycle(walkIndex, 0, 23);
            image = walk.getCurImage(walkIndex / 6, dirp, Direction.S);
            Vector towardsPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), 2);
            velX = towardsPlayer.getHorizComp();
            velY = towardsPlayer.getVertComp();
            xo = 0;
            yo = 0;
        } else if(state == State.Attack) {
            Direction cdir = toPlayer.getCardinalDirection();
            attackTimer++;
            if(attackTimer < 10) {
                attackIndex = 0;
            } else if (attackTimer == 15) {
                velX = 15 * Math.signum(toPlayer.getHorizComp());
                velY = 15 * Math.signum(toPlayer.getVertComp());
            } else if (attackTimer < 15) {
                attackIndex = 1;
            } else if (attackTimer < 25) {
                attackIndex = 2;
            } else if (attackTimer < 50) {
                float spray = Vector.atan2From360((float)Math.toRadians((attackTimer - 24) * 14.4));
                velX = 0;
                velY = 0;
                attackIndex = 3;
                EntityWormBullet proj = new EntityWormBullet(getXMid(), getYMid(), game, rock);
                Vector shot = new Vector(spray, 4);
                proj.setVelX(shot.getHorizComp());
                proj.setVelY(shot.getVertComp());
                handler.addEnt(proj);
            } else if (attackTimer < 80) {
                attackIndex = 4;
            } else {
                state = State.Walk;
                attackTimer = 0;
                attackIndex = 0;
            }
            xo = 16;
            yo = 80;
            image = attack.getCurImage(attackIndex, cdir, Direction.S);
        }
        x += velX;
        y += velY;
        doDamage();

        if(lastHealth != health && state == State.Idle) {
            state = State.Walk;
            drawBar = true;
            game.audioPlayer.playAudio("DungeonTrack7.wav");
        }
        lastHealth = health;
        if (health <= 0) {
            handler.delEnt(this);
            game.player.schmoney += 100;
            game.player.setWrath(game.player.getWrath() - 20);
        }

    }

    public Direction getDirectionToPlayer() {
        Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        return vector.getDirection();
    }


    public void render(Graphics g) {

    }

    public void renderHUD(Graphics g) {
        if(drawBar) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(2, 2);
            BufferedImage bar = Lib.getImage("src/resources/display/goatBar.png");
            BufferedImage bartop = Lib.getImage("src/resources/display/goatBarHealth.png");
            g.drawImage(bar, 0, 10, null);
            int health138 = (int) (health / 3000.0 * 138.0);
            g.drawImage(bartop.getSubimage(0, 0, 11 + health138, 110), 0, 10, null);
            g2d.scale(.5, .5);
        }
    }

    public void renderTop(Graphics g) {
        g.drawImage(image, (int) x - xo, (int) y - yo, null);
    }

}


