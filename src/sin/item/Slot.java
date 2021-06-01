package sin.item;

import sin.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Name: Slot.java
 * Purpose: Holds and displays items.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class Slot {

    public Stack stack;
    public ItemType type;
    public boolean selected;
    int x, y;

    public Slot(Stack stack, int x, int y, ItemType acceptedType) {
        this.stack = stack;
        this.type = acceptedType;
        this.x = x;
        this.y = y;
    }

    public Slot(Stack stack, int x, int y) {
        this(stack, x, y, ItemType.Resource);
    }

    public Slot(int x, int y, ItemType acceptedType) {
        this(null, x, y, acceptedType);
    }

    public Slot(int x, int y) {
        this(null, x, y);
    }

    public void drawCount(Game game, Graphics g) {
        ArrayList<BufferedImage> nums = game.inventory.intToImages(stack.count);
        int l = nums.size();
        int xc = this.x + 16;
        int yc = this.y + 16;
        for(int i = 0; i < l; i++) {
            g.drawImage(nums.get(i), xc - (l * 4) + i * 4 + 1, yc - 5, null);
        }
    }

    public void render(Game game, Graphics g) {
        Color slotColor = new Color(211, 211, 211);
        Color selectColor = new Color(109, 109, 109, game.cycle20 * 255 / 20);
        if(stack != null) {
            g.setColor(slotColor);
            g.fillRect(x, y, 16, 16);
            g.drawImage(stack.item.image, x, y, null);
            if(stack != null && stack.count > 1) {
                drawCount(game, g);
            }

        }
        if(selected) {
            g.setColor(selectColor);
            g.fillRect(x+0, y+0, 3, 1);
            g.fillRect(x+0, y+1, 1, 2);
            g.fillRect(x+13, y+0, 3, 1);
            g.fillRect(x+15, y+1, 1, 2);
            g.fillRect(x+0, y+13, 1, 3);
            g.fillRect(x+1, y+15, 2, 1);
            g.fillRect(x+13, y+15, 3, 1);
            g.fillRect(x+15, y+13, 1, 2);
        }
    }

    public Item getItem() {
        if (stack != null) {
            return stack.item;
        }
        return null;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);

    }
}
