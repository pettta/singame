package sin.materia.entity;

import sin.Game;
import sin.lib.EntityType;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.materia.sprite.Polysprite;

import java.awt.*;

public class WormShooter extends Entity {

    private int shootCounter;
    private int spriteIndex;
    Polysprite ps;

    public WormShooter(float x, float y, Game game) {
        super(x, y, 16, 22, EntityType.Enemy, game);
        this.speed = 10;
        ps = new Polysprite("entities/worm.png",15, 1, width, height);
        image = ps.getCurImage(0);
        hb = new Rectangle((int) x, (int) y + 6, width, height - 6);
    }

    public void tick() {
        shootCounter++;
        Vector toPlayer = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid());
        float distance = toPlayer.getMagnitude();
        if(shootCounter >= 10 && distance < 125) {
            WormBullet proj = new WormBullet(getXMid(), getYMid(), game, ps.getCurImage(14));
            Vector vector = new Vector(getXMid(), getYMid(), game.player.getXMid(), game.player.getYMid(), speed);
            proj.setVelX(vector.getHorizComp());
            proj.setVelY(vector.getVertComp());
            handler.addEnt(proj);
            shootCounter = 0;
        }
        spriteIndex = Lib.cycle(spriteIndex, 0, 13);
        image = ps.getCurImage(spriteIndex);
    }

    public void render(Graphics g) {
        g.drawImage(image.getSubimage(0, 6, 16, 16), (int) x, (int) y + 6, null);
    }

    public void renderTop(Graphics g) {
        g.drawImage(image.getSubimage(0, 0, 16, 6), (int) x, (int) y, null);
    }

}
