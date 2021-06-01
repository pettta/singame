package sin.item;

import sin.Game;
import sin.Handler;
import sin.lib.Direction;
import sin.lib.Vector;
import sin.mundus.materia.entity.EntityBomb;
import sin.mundus.materia.entity.EntityPlayer;
/**
 * Name: ItemBomb.java
 * Purpose: A bomb item that throws an exploding bomb when activated.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class ItemBomb extends ItemSpecial {

    public ItemBomb(String name, int maxStack) {
        super(name, maxStack);
    }

    public void onEquip() {

    }

    public void onUse(Game game) {

        Handler handler = game.handler;
        EntityPlayer p = game.player;
        Direction direction = p.getRoughDirection() == Direction.None ? p.lastDirection : p.getRoughDirection();
        int xs = Vector.xSignumFromDirection(direction);
        int ys = Vector.ySignumFromDirection(direction);

        EntityBomb proj = new EntityBomb(p.getXMid() - 8 + xs * 16, p.getYMid() - 8 + ys * 24, game);
        proj.setVelX(xs * 6);
        proj.setVelY(ys * 6);
        handler.addEnt(proj);

    }

}
