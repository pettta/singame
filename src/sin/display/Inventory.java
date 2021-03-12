package sin.display;

import sin.Game;
import sin.item.ItemStack;
import sin.item.ItemType;
import sin.item.Registry;
import sin.item.Slot;
import sin.lib.Lib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Inventory {

    Game game;
    BufferedImage background;

    ArrayList<Slot> slots;

    public Color[] sinBackgrounds;
    public Color[] sinForegrounds;

    public Inventory(Game game) {
        this.game = game;
        this.background = Lib.getImage("src/resources/display/inventory.png");
        initSlots();
        initColors();
        loadInventory();
    }

    public void initSlots() {
        slots = new ArrayList<Slot>();
        slots.add(new Slot(6, 31, ItemType.Melee));
        slots.add(new Slot(27, 31, ItemType.Ranged));
        slots.add(new Slot(48, 31, ItemType.Armor));
        slots.add(new Slot(69, 31, ItemType.Special));
        for(int i = 6; i < 70; i+=21) {
            slots.add(new Slot(i, 73));
        }
        for(int i = 6; i < 70; i+=21) {
            slots.add(new Slot(i, 94));
        }
    }

    public void initColors() {
        sinBackgrounds = new Color[7];
        sinBackgrounds[0] = new Color(57, 42, 73);
        sinBackgrounds[1] = new Color(135, 112, 0);
        sinBackgrounds[2] = new Color(119, 0, 0);
        sinBackgrounds[3] = new Color(0, 91, 16);
        sinBackgrounds[4] = new Color(145, 0, 140);
        sinBackgrounds[5] = new Color(145, 82, 0);
        sinBackgrounds[6] = new Color(0, 121, 145);
        sinForegrounds = new Color[7];
        sinForegrounds[0] = new Color(189, 127, 255);
        sinForegrounds[1] = new Color(224, 214, 170);
        sinForegrounds[2] = new Color(255, 96, 96);
        sinForegrounds[3] = new Color(118, 204, 132);
        sinForegrounds[4] = new Color(226, 172, 225);
        sinForegrounds[5] = new Color(247, 190, 121);
        sinForegrounds[6] = new Color(125, 237, 255);
    }

    public void loadInventory() {
        slots.get(0).stack = new ItemStack(Registry.dagger, 1);
        slots.get(3).stack = new ItemStack(Registry.bomb, 1);
    }

    public void renderSinBars(Graphics g) {
        int b = 0;
        for(int i = 10; i < 107; i+=16) {
            g.setColor(sinBackgrounds[b]);
            g.fillRect(108, i, 45, 4);
            g.setColor(sinForegrounds[b]);
            g.fillRect(108, i, (int) game.player.sins[b], 4);
            b++;
        }
    }

    public void renderSlots(Graphics g) {
        for(int i = 0; i < slots.size(); i++) {
            slots.get(i).render(g);
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(2, 2);
        renderSinBars(g);
        g.drawImage(background, 0, 0, null);
        renderSlots(g);
        g2d.scale(.5, .5);

    }

    public Slot getMelee() {
        return slots.get(0);
    }

    public Slot getRanged() {
        return slots.get(1);
    }

    public Slot getArmor() {
        return slots.get(2);
    }

    public Slot getSpecial() {
        return slots.get(3);
    }

}
