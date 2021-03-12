package sin.item;

import sin.Game;
import sin.lib.Lib;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Item {

    public int maxStack;
    public ItemType type;
    public BufferedImage image;
    public String name;

    public Item(String name, int maxStack, ItemType type) {
        this.name = name;
        this.maxStack = maxStack;
        this.type = type;
        this.image = Lib.getImage("src/resources/items/" + name + ".png");
    }
}
