package sin.item;

import sin.Game;
import sin.Handler;
import sin.lib.Direction;
import sin.lib.Vector;
import sin.mundus.materia.entity.EntityBomb;
import sin.mundus.materia.entity.EntityPlayer;
/**
 * Name: ItemSpecial.java
 * Purpose: Base class for all special items.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class ItemSpecial extends ItemEquipment {

    public ItemSpecial(String name, int maxStack) {
        super(name, maxStack, ItemType.Special);
    }

    public void onEquip() {

    }

    public void onUse(Game game) {


    }

}
