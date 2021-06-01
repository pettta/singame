package sin.item;

import sin.Game;
import sin.Handler;
import sin.lib.Vector;
import sin.mundus.materia.entity.EntityPlayer;
import sin.mundus.materia.entity.EntityRangedShot;

/**
 * Name: ItemRanged.java
 * Purpose: Item ranged base. Shoots a projectile on use.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */

public class ItemRanged extends ItemEquipment {

    int projtype;
    // 0 - arrow
    // 1 - ball
    public ItemRanged(String name, int projtype) {
        super(name, 1, ItemType.Ranged);
        this.projtype = projtype;
    }

    public void onEquip() {

    }

    public void onUse(Game game, int x, int y) {
        Handler handler = game.handler;
        EntityPlayer p = game.player;
        EntityRangedShot proj = new EntityRangedShot(p.getXMid() - 4, p.getYMid() - 4, game, projtype);
        Vector vector = new Vector(p.getXMid() - 4, p.getYMid() - 4, x, y, 20);
        proj.setVelX(vector.getHorizComp());
        proj.setVelY(vector.getVertComp());
        handler.addEnt(proj);

    }

}
