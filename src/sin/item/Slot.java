package sin.item;

import java.awt.*;

public class Slot {

    public ItemStack stack;
    public ItemType type;

    public Slot(ItemStack stack, ItemType acceptedType) {
        this.stack = stack;
        this.type = acceptedType;
    }

    public void draw(Graphics g, int x, int y) {
        Color slotColor = new Color(211, 211, 211);
        if(stack != null) {
            g.setColor(slotColor);
            g.fillRect(x, y, 16, 16);
            g.drawImage(stack.item.image, x, y, null);
        }
    }
}
