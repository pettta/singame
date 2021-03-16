package sin.item;

public class Registry {

    public static final Item bomb = new Item("bomb", 20, ItemType.Special);
    public static final Item azulShard = new Item("azulShard", 99, ItemType.Misc);
    public static final Item wormHide = new Item("wormHide", 99, ItemType.Misc);
    public static final Item dagger = new ItemMelee("dagger", 20, 20, 16);
    public static final Item fireSword = new ItemMelee("fireSword", 20, 20, 16);
    public static final Item grassBlade = new ItemMelee("grassBlade", 20, 20, 16);
    public static final Item waterSword = new ItemMelee("waterSword", 20, 20, 16);
    public static final Item shoddyBow = new Item("shoddyBow", 1, ItemType.Ranged);
    public static final Item oldSlingshot = new Item("oldSlingshot", 1, ItemType.Ranged);
    public static final Item shadeCrystalArmor = new Item("shadeCrystalArmor", 1, ItemType.Armor);

}
