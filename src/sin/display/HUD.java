package sin.display;

import sin.Game;
import sin.lib.Lib;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {

    //public static int health = 100;
    Game game;
    BufferedImage barLeft;
    BufferedImage barEnd;
    Color brightPink;
    Color darkPink;
    Color gray;

    public HUD(Game game) {
        this.game = game;
        BufferedImage healthBar = Lib.getImage("src/resources/display/health.png");
        barLeft = healthBar.getSubimage(0, 0, 17, 16);
        barEnd = healthBar.getSubimage(17, 0, 5, 16);
        brightPink = new Color(255, 25, 90);
        darkPink = new Color(168, 16, 59);
        gray = new Color(128, 128,128);
    }

    public void tick() {

    }

    public void drawHealth(Graphics g, int x, int y) {
        int mh = (int) game.player.maxHealth;
        int h = (int) game.player.getHealth();
        int midBarLength = mh - 5;
        g.setColor(gray);
        g.fillRect(x + 17, y, midBarLength + 1, 16);
        g.setColor(darkPink);
        g.fillRect(x + 13, y + 2, mh, 12);
        g.setColor(brightPink);
        g.fillRect(x + 13, y + 2, h, 12);
        g.drawImage(barLeft, x, y, null);
        g.drawImage(barEnd, x + 17 + midBarLength, y, null);
    }

    public void render(Graphics g) {
        drawHealth(g,8, 8);

    }
}
