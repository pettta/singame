package sin.display;

import sin.Game;
import sin.lib.Lib;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dialogue {

    public boolean dialogue;
    public Game game;
    public BufferedImage box;


    public Dialogue(Game game) {
        this.game = game;
        box = Lib.getImage("src/resources/display/textBox.png");
        dialogue = true;
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(2, 2);
        g.drawImage(box,0, 0, null);
        g2d.scale(.5, .5);
        g.drawString("This ia test string that probably looks dogshit!", 36, 180);
    }

    public void tick() {

    }

}
