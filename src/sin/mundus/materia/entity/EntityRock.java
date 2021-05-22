package sin.mundus.materia.entity;

import sin.Game;
import sin.lib.Lib;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityRock extends Entity {

    BufferedImage image;

    public EntityRock(float x, float y, Game game) {
        super(x, y, 16, 16, EntityType.Rock, game);
        image = Lib.getImage("src/resources/entities/rockstacle.png");
    }

    public void onInteract(int id) {
        if(id == 4) {
            game.handler.delEnt(this);
        }
    }

    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

}
