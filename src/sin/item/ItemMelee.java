package sin.item;

public class ItemMelee extends Item {

    public int damage, swingSpeed, range;

    public ItemMelee(String name, int damage, int swingSpeed, int range) {
        super(name, 1, ItemType.Melee);
    }

    public void onUse() {

    }

}
