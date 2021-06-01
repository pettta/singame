package sin.mundus.materia.entity;

import sin.Game;
import sin.item.Item;
import sin.item.Stack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * Name: EntityStack.java
 * Purpose: A stack on the ground that can be picked up and added to the player's inventory.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class EntityStack extends Entity {

    BufferedImage image;
    Stack stack;

    Stack taken;

    int itemdisplaytimer;
    int invfulltimer;
    int countTaken;
    Font font;
    int deathcounter;
    boolean dying;


    public EntityStack(float x, float y, Game game, Stack stack) {
        super(x, y, 16, 16, EntityType.Item, game);
        this.stack = stack;
        image = stack.item.image;
        deathcounter = 50;

    }

    public void tick() {
        if(dying) {
            deathcounter--;
        }
        if(deathcounter < 0) {
            handler.delEnt(this);
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

    public void onInteract(int id) {
        Item itembefore = stack.item;
        int countbefore = stack.count;
        Stack remaining = game.inventory.addStack(stack);
        if (remaining == null) {
            dying = true;
            taken = new Stack(itembefore, countbefore);
        } else {
            taken = new Stack(itembefore, countbefore - remaining.count);
            invfulltimer = 50;
        }
        stack = remaining;
        itemdisplaytimer = 50;
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

    public void render(Graphics g) {
        if(!dying) {
            g.drawImage(image, (int) x, (int) y, null);
        }
    }
}
