package sin.materia.entity;

import sin.Game;
import sin.lib.EntityType;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.materia.sprite.Polysprite;

public class WormShooter extends Entity {

    private int shootCounter;
    private int spriteIndex;
    Polysprite ps;

    public WormShooter(float x, float y, Game game) {
        super(x, y, 16, 22, EntityType.Enemy, game);
        this.speed = 10;
        ps = new Polysprite("entities/worm.png",15, 1, width, height);
        image = ps.getCurImage(0);
    }

    public void tick() {
        super.tick();
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

}
