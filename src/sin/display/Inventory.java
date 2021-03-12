package sin.display;

import sin.Game;
import sin.item.ItemStack;
import sin.item.ItemType;
import sin.item.Registry;
import sin.item.Slot;
import sin.lib.Lib;
import sin.mundus.materia.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Inventory {

    Game game;
    BufferedImage background;

    Color slotColor;

    ArrayList<Slot> invSlots;

    //public float lust, gluttony, greed, sloth, wrath, envy, pride;
    public Color[] sinBackgrounds;
    public Color[] sinForegrounds;


    public Inventory(Game game) {
        this.game = game;
        this.background = Lib.getImage("src/resources/display/inventory.png");
        invSlots = new ArrayList<Slot>();
        invSlots.add(new Slot(null, ItemType.Melee));
        invSlots.add(new Slot(null, ItemType.Ranged));
        invSlots.add(new Slot(null, ItemType.Armor));
        invSlots.add(new Slot(null, ItemType.Special));
        for(int i = 0; i < 8; i++) {
            invSlots.add(new Slot(null, ItemType.Misc));
        }
        slotColor = new Color(211, 211, 211);
        sinBackgrounds = new Color[7];
        sinForegrounds = new Color[7];
        sinBackgrounds[0] = new Color(57, 42, 73);
        sinBackgrounds[1] = new Color(135, 112, 0);
        sinBackgrounds[2] = new Color(119, 0, 0);
        sinBackgrounds[3] = new Color(0, 91, 16);
        sinBackgrounds[4] = new Color(145, 0, 140);
        sinBackgrounds[5] = new Color(145, 82, 0);
        sinBackgrounds[6] = new Color(0, 121, 145);

        sinForegrounds[0] = new Color(189, 127, 255);
        sinForegrounds[1] = new Color(224, 214, 170);
        sinForegrounds[2] = new Color(255, 96, 96);
        sinForegrounds[3] = new Color(118, 204, 132);
        sinForegrounds[4] = new Color(226, 172, 225);
        sinForegrounds[5] = new Color(247, 190, 121);
        sinForegrounds[6] = new Color(125, 237, 255);

        invSlots.get(0).stack = new ItemStack(Registry.dagger, 1);
        invSlots.get(3).stack = new ItemStack(Registry.bomb, 1);

    }


    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(2, 2);
        //108 x: y (10, 26, 42... 106)
        int b = 0;
        for(int i = 10; i < 107; i+=16) {
            g.setColor(sinBackgrounds[b]);
            g.fillRect(108, i, 45, 4);
            g.setColor(sinForegrounds[b]);
            g.fillRect(108, i, (int) game.player.sins[b], 4);
            b++;
        }
        g.drawImage(background, 0, 0, null);
        // (6, 31) (27, 31) (48, 31) (69, 31)
        int c = 0;
        for(int i = 6; i < 70; i+=21) {
            invSlots.get(c).draw(g, i, 31);
            c++;
        }
        for(int i = 6; i < 70; i+=21) {
            invSlots.get(c).draw(g, i, 73);
            c++;
        }
        for(int i = 6; i < 70; i+=21) {
            invSlots.get(c).draw(g, i, 94);
            c++;
        }
        g2d.scale(.5, .5);

    }

    public Slot getMelee() {
        return invSlots.get(0);
    }

    public Slot getRanged() {
        return invSlots.get(1);
    }

    public Slot getArmor() {
        return invSlots.get(2);
    }

    public Slot getSpecial() {
        return invSlots.get(3);
    }

}
