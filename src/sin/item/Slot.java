package sin.item;

import java.awt.*;

public class Slot {

    public ItemStack stack;
    public ItemType type;
    int x, y;

    public Slot(ItemStack stack, int x, int y, ItemType acceptedType) {
        this.stack = stack;
        this.type = acceptedType;
        this.x = x;
        this.y = y;
    }

    public Slot(ItemStack stack, int x, int y) {
        this(stack, x, y, ItemType.Misc);
    }

    public Slot(int x, int y, ItemType acceptedType) {
        this(null, x, y, acceptedType);
    }

    public Slot(int x, int y) {
        this(null, x, y);
    }

    public void render(Graphics g) {
        Color slotColor = new Color(211, 211, 211);
        if(stack != null) {
            g.setColor(slotColor);
            g.fillRect(x, y, 16, 16);
            g.drawImage(stack.item.image, x, y, null);
        }
    }
}
