package sin.item;

import sin.Game;
import sin.Registry;
import sin.lib.Lib;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
