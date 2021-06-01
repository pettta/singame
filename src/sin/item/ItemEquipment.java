package sin.item;

import sin.Game;
/**
 * Name: ItemEquipment.java
 * Purpose: Base class for all equipment, e.g. armor, swords, ranged weapons.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class ItemEquipment extends Item {

    public ItemEquipment(String name, int maxStack, ItemType type) {
        super(name, maxStack, type);
    }

    public void onEquip() {

    }

    public void onUse(Game game) {

    }

}
