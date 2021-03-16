package sin.display;

import sin.Game;
import sin.item.Stack;
import sin.item.Registry;
import sin.item.Slot;
import sin.lib.Coord;
import sin.lib.Lib;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Inventory {

    Game game;
    BufferedImage background;
    BufferedImage slot;
    BufferedImage arrows;

    // Invariable Slots
    ArrayList<Slot> equipmentSlots;
    ArrayList<Slot> resourceSlots;
    // Variable Slots
    ArrayList<Slot> armorySlots;

    ArrayList<ArrayList<Stack>> armoryStacks;


    public int armoryIndex;

    public Slot selected;

    public Color[] sinBackgrounds;
    public Color[] sinForegrounds;

    public BufferedImage[] nums;

    public Inventory(Game game) {
        this.game = game;
        this.background = Lib.getImage("src/resources/display/inventory.png");
        this.slot = Lib.getImage("src/resources/display/slot.png");
        this.arrows = Lib.getImage("src/resources/display/arrows.png");
        loadNums();
        initSlots();
        initColors();
        loadInventory();
    }

    public void loadNums() {
        nums = new BufferedImage[10];
        BufferedImage allNums = Lib.getImage("src/resources/display/nums.png");
        for(int i = 0; i < 10; i++) {
            nums[i] = allNums.getSubimage(i * 3, 0, 3, 5);
        }
    }


    public ArrayList<BufferedImage> intToImages(int i) {
        ArrayList<Integer> digits = new ArrayList<Integer>();
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        while (i > 0) {
            digits.add(i % 10);
            i /= 10;
        }
        for(int j = digits.size() - 1; j >= 0; j--) {
            images.add(nums[digits.get(j)]);
        }
        return images;
    }

    public void initSlots() {
        equipmentSlots = new ArrayList<Slot>();
        armorySlots = new ArrayList<Slot>();
        resourceSlots = new ArrayList<Slot>();
        for(int i = 6; i < 70; i += 21) {
            equipmentSlots.add(new Slot(i, 18));
            armorySlots.add(new Slot(i, 39));
            resourceSlots.add(new Slot(i, 73));

        }
        for(int i = 6; i < 70; i += 21) {
            resourceSlots.add(new Slot(i, 94));
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
        armoryStacks = new ArrayList<ArrayList<Stack>>();
        armoryStacks.add(new ArrayList<Stack>());
        armoryStacks.add(new ArrayList<Stack>());
        armoryStacks.add(new ArrayList<Stack>());
        armoryStacks.add(new ArrayList<Stack>());


        armoryStacks.get(0).add(new Stack(Registry.grassBlade));
        armoryStacks.get(0).add(new Stack(Registry.waterSword));
        armoryStacks.get(0).add(new Stack(Registry.fireSword));
        armoryStacks.get(0).add(new Stack(Registry.dagger));

        armoryStacks.get(1).add(new Stack(Registry.oldSlingshot));
        armoryStacks.get(1).add(new Stack(Registry.shoddyBow));

        armoryStacks.get(2).add(new Stack(Registry.shadeCrystalArmor));

        armoryStacks.get(3).add(new Stack(Registry.bomb));

        resourceSlots.get(0).stack = new Stack(Registry.azulShard, 2);
        resourceSlots.get(1).stack = new Stack(Registry.wormHide, 99);

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

    public void renderInvariableSlots(Graphics g) {
        for(int i = 0; i < equipmentSlots.size(); i++) {
            equipmentSlots.get(i).render(game, g);
        }
        for(int i = 0; i < resourceSlots.size(); i++) {
            resourceSlots.get(i).render(game, g);
        }
    }

    public void renderVariableSlots(Graphics g) {
        for(int i = 4; i < 68; i += 21) {
            g.drawImage(slot, i, 37, null);
        }
        for(int i = 0; i < armorySlots.size(); i++) {
            armorySlots.get(i).render(game, g);
        }
        g.drawImage(arrows, 28, 60, null);
    }

    public void render(Graphics g) {
        //System.out.println(selected);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(2, 2);
        renderSinBars(g);
        g.drawImage(background, 0, 0, null);
        if(equipmentSlots.contains(selected)) {
            renderVariableSlots(g);
        }
        renderInvariableSlots(g);
        g2d.scale(.5, .5);
        game.hud.drawHealth(g, 8, 8);

    }

    public void updateArmorySlots(ArrayList<Stack> stack) {
        for(int j = 0; j < armorySlots.size(); j++) {
            if(j < stack.size()) {
                armorySlots.get(j).stack = stack.get(j);
            } else {
                armorySlots.get(j).stack = null;
            }
        }
    }

    public void doSelect(Slot slot) {
        if(selected != slot) {
            slot.selected = true;
            if (selected != null) {
                selected.selected = false;
            }
            selected = slot;
        } else {
            slot.selected = false;
            selected = null;
        }
    }

    public void mousePressed(MouseEvent e) {
        Coord gamePos = game.getGamePos(e.getX(), e.getY());
        int x = gamePos.x / 2;
        int y = gamePos.y / 2;
        for (int i = 0; i < equipmentSlots.size(); i++) {
            Slot equipmentSlot = equipmentSlots.get(i);
            if(Lib.locIn(x, y, equipmentSlot.getBounds())) {
                doSelect(equipmentSlot);
                updateArmorySlots(armoryStacks.get(i));
            }
        }
        for (int i = 0; i < resourceSlots.size(); i++) {
            Slot resourceSlot = resourceSlots.get(i);
            if(Lib.locIn(x, y, resourceSlot.getBounds())) {
                doSelect(resourceSlot);
            }
        }
        if(equipmentSlots.contains(selected)) {
            for (int i = 0; i < armorySlots.size(); i++) {
                Slot armorySlot = armorySlots.get(i);
                if(Lib.locIn(x, y, armorySlot.getBounds())) {
                    ArrayList<Stack> armoryStack = armoryStacks.get(equipmentSlots.indexOf(selected));
                    armoryStack.remove(armorySlot.stack);
                    if(selected.stack != null) {
                        armoryStack.add(i < armoryStack.size() ? i : armoryStack.size(), selected.stack);
                    }
                    selected.stack = armorySlot.stack;



                    updateArmorySlots(armoryStack);


                    game.player.updateImage();

                }
            }
        }
    }

    public Slot getMeleeSlot() {
        return equipmentSlots.get(0);
    }

    public Slot getRangedSlot() {
        return equipmentSlots.get(1);
    }

    public Slot getArmorSlot() {
        return equipmentSlots.get(2);
    }

    public Slot getSpecialSlot() {
        return equipmentSlots.get(3);
    }

    public ArrayList<Stack> getMeleeStacks() {
        return armoryStacks.get(0);
    }

    public ArrayList<Stack> getRangedStacks() {
        return armoryStacks.get(1);
    }

    public ArrayList<Stack> getArmorStacks() {
        return armoryStacks.get(2);
    }

    public ArrayList<Stack> getSpecialStacks() {
        return armoryStacks.get(3);
    }

    /*
    public void addStackToSlot(Slot slot, Stack stack) {
        if(slot.stack == null) {
            slot.stack = stack;
        } else {
            slot.stack.count += stack.count;
        }
    }
    public void addStack(Stack stack) {
        Slot firstMatch = null;
        for(int i = 0; i < slots.size(); i++) {
            Slot curSlot = slots.get(i);
            if(curSlot.stack == null || curSlot.stack.item == stack.item) {
                if(curSlot.stack.item.maxStack >= stack.count + curSlot.stack.count)
                firstMatch = curSlot;
                break;
            }
        }
        if(firstMatch != null) {
            addStackToSlot(firstMatch, stack);
        }
    }
    */

}
