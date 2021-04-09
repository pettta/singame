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
    String name;

    public Item(String name, int maxStack) {
        this.name = name;
        this.maxStack = maxStack;
        this.image = Lib.getImage("src/resources/items/" + name + ".png");
    }

    public String getName() {
        return name;
    }

    public Item(String name) {
        this(name, 99);
    }

}
