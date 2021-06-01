package sin.item;

import sin.Game;
import sin.Handler;
import sin.lib.Direction;
import sin.lib.Vector;
import sin.mundus.materia.entity.EntityBomb;
import sin.mundus.materia.entity.EntityPlayer;

/**
 * Name: ItemPotion.java
 * Purpose: A potion item. Heals player on activation.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */

public class ItemPotion extends ItemSpecial {

    public ItemPotion(String name) {
        super(name, 50);
    }

    public void onEquip() {

    }

    public void onUse(Game game) {
        if(game.player.maxHealth > game.player.health + 10) {
            game.player.health += 10;
            game.inventory.equipmentSlots.get(3).stack.count -= 1;
            if (game.inventory.equipmentSlots.get(3).stack.count == 0) {
                game.inventory.equipmentSlots.get(3).stack = null;
            }
        }

    }

}
