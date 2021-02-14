package sin.display;

import sin.lib.Lib;

import java.awt.*;

public class HUD {

    public static int health = 200;
    private int green = 255;

    public void tick() {
        health = Lib.clamp(health, 0, 200);
        green = Lib.clamp(green, 0 ,255);
        green = health;
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, green, 0));
        g.fillRect(15, 15, health, 32);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 32);
    }
}
