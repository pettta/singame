package sin.item;

import sin.Game;
import sin.Handler;
import sin.mundus.materia.entity.Entity;
import sin.mundus.materia.entity.EntityPlayer;

import java.awt.*;

/**
 * Name: ItemMelee.java
 * Purpose: Base class for all melee weapon. Deals damage on activation.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class ItemMelee extends ItemEquipment {

    public int damage, swingDelay, range;

    public ItemMelee(String name, int damage, int swingDelay, int range) {
        super(name, 1, ItemType.Melee);
        this.damage = damage;
        this.swingDelay = swingDelay;
        this.range = range;
    }

    public void onEquip() {

    }

    public void onUse(Game game) {
        Handler handler = game.handler;
        EntityPlayer p = game.player;
        Rectangle rect = new Rectangle((int) p.getX() - 20, (int) p.getY() - 20, p.getWidth() + 40, p.getHeight() + 40);
        for(int i = 0; i < handler.getList().size(); i++) {
            Entity ent = handler.getList().get(i);
            if(rect.intersects(ent.getBounds()) && ent != game.player) {
                ent.health -= 275 + p.getWrath() * .1;
                ent.damaged();
            }
        }
    }

}
