package sin;

import sin.item.*;
import sin.mundus.materia.entity.Species;

import java.util.HashMap;

public class Registry {

    public static HashMap<String, Item> items = new HashMap<String, Item>();

    public static final Item bomb = new ItemSpecial("bomb", 20);
    public static final Item azulShard = new Item("azulShard", 99);
    public static final Item wormHide = new Item("wormHide", 99);
    public static final Item dagger = new ItemMelee("dagger", 20, 20, 16);
    public static final Item fireSword = new ItemMelee("fireSword", 20, 20, 16);
    public static final Item grassBlade = new ItemMelee("grassBlade", 20, 20, 16);
    public static final Item waterSword = new ItemMelee("waterSword", 20, 20, 16);
    public static final Item shoddyBow = new ItemRanged("shoddyBow", 0);
    public static final Item oldSlingshot = new ItemRanged("oldSlingshot", 1);
    public static final Item shadeCrystalArmor = new ItemArmor("shadeCrystalArmor");

    public void register() {
        registerItem(bomb);
        registerItem(azulShard);
        registerItem(wormHide);
        registerItem(dagger);
        registerItem(fireSword);
        registerItem(grassBlade);
        registerItem(waterSword);
        registerItem(shoddyBow);
        registerItem(oldSlingshot);
        registerItem(shadeCrystalArmor);
    }

    public void registerItem(Item item) {
        items.put(item.getName(), item);
    }
}
