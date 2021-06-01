package sin.mundus.materia.entity;

import sin.Game;
import sin.item.Item;
import sin.item.Stack;
import sin.lib.Lib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Name: EntityChest.java
 * Purpose: Openable chest that stores and gives player a stack on interaction.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class EntityChest extends Entity {

    BufferedImage image;
    Stack stack;
    Stack taken;
    int count;
    int itemdisplaytimer;
    int invfulltimer;
    int countTaken;
    Font font;

    public EntityChest(float x, float y, Game game, Stack stack) {
        super(x, y, 16, 16, EntityType.Chest, game);
        this.stack = stack;
        image = Lib.getImage("src/resources/entities/chest.png");
        this.hb = getBounds();
        Font newFont = null;
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/pixeled.ttf")).deriveFont(9.5F);
        } catch (IOException | FontFormatException e) {
            System.out.println("Font not found!");
        }
        font = newFont;
    }

    public void tick() {
        if(itemdisplaytimer > 0) {
            itemdisplaytimer--;

        }
        if(invfulltimer > 0) {
            invfulltimer--;
        }
    }

    public void onInteract(int id) {
        if(stack != null) {
            Item itembefore = stack.item;
            int countbefore = stack.count;
            Stack remaining = game.inventory.addStack(stack);
            if (remaining == null) {
                taken = new Stack(itembefore, countbefore);
            } else {

                taken = new Stack(itembefore, countbefore - remaining.count);
                invfulltimer = 50;
            }
            stack = remaining;
            itemdisplaytimer = 50;
        }
    }

    public void drawCount(Game game, Graphics g) {
        int x = 10;
        int y = 210;
        ArrayList<BufferedImage> nums = game.inventory.intToImages(taken.count);
        int l = nums.size();
        int xc = 10 + 16;
        int yc = 210 + 16;
        for(int i = 0; i < l; i++) {
            g.drawImage(nums.get(i), xc - (l * 4) + i * 4 + 1, yc - 5, null);
        }
    }

    public void render(Graphics g) {
        if(stack != null) {
            g.drawImage(image.getSubimage(0, 0, 16, 16), (int) x, (int) y, null);
        } else {
            g.drawImage(image.getSubimage(16, 0, 16, 16), (int) x, (int) y, null);
        }


    }

    public void renderHUD(Graphics g) {
        int x = 10;
        int y = 210;
        if(itemdisplaytimer > 0) {
            g.drawImage(taken.item.image, x, y, null);
            System.out.println(taken.count);
            if (taken != null && taken.count > 1) {
                System.out.println("Count being drawn.");
                drawCount(game, g);
            }


        }
        if(invfulltimer > 0) {
            System.out.println("Full inv message.");
            g.drawString("Inventory full!", 10, 200);
        }
    }
}
