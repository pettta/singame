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
/**
 * Name: EntityDragon.java
 * Purpose: Renders and ticks the dragon boss entity.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class EntityDragon extends Entity {

    int idleIndex;

    float lastHealth;

    Polysprite idle;


    @Override
    public void onInteract(int id) {
        game.dialogue.talk(this, "Thanks for playing the demo!!!!");
    }

    private void doDamage() {
        for (int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if (ent.getType() == EntityType.Rock || ent.getType() == EntityType.Shot) {
                if (getBounds().intersects(ent.getBounds())) {
                    handler.delEnt(ent);
                    // Add line for damage animation here i think?
                    health -= 34;
                }
            }
        }
    }

    public EntityDragon(float x, float y, Game game) {
        super(x, y, 32, 64, EntityType.Boss, game);

        this.health = 3000;
        this.lastHealth = 3000;
        idle = new Polysprite("entities/Dragon.png",8, 1, 128, 80);

        image = idle.getCurImage(0);
        hb = new Rectangle((int) x, (int) y, width, height);

    }
    public JSONObject write(JSONObject obj) {
        JSONObject extra = super.write(obj);
        return extra;
    }

    public ISaveable read(JSONObject obj) {
        super.read(obj);
        return this;
    }


    public void tick() {

        idleIndex = Lib.cycle(idleIndex, 0 ,23);
        image = idle.getCurImage(idleIndex / 3, Direction.N);

        if(lastHealth != health) {
            game.audioPlayer.playAudio("zachmediocretheme.wav");
            game.dialogue.talk(this, "Thanks for playing the demo!!!");
        }
        lastHealth = health;
        doDamage();

    }

    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }


    public void renderTop(Graphics g) {

    }

}


