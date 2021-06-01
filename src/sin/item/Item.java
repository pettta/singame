package sin.item;

import sin.Game;
import sin.Registry;
import sin.lib.Lib;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Name: Item.java
 * Purpose: Serves as the base class for all items and manages their universal functionality.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class Item {

    public int maxStack;
    public BufferedImage image;
    public ItemType type;
    String name;

    public Item(String name, int maxStack, ItemType type) {
        this.name = name;
        this.type = type;
        this.maxStack = maxStack;
        this.image = Lib.getImage("src/resources/items/" + name + ".png");
    }

    public Item(String name, int maxStack) {
        this(name, maxStack, ItemType.Resource);
    }

    public String getName() {
        return name;
    }

    public Item(String name, ItemType type) {
        this(name, 99, type);
    }

}
