package sin.display;

import sin.Game;
import sin.lib.Lib;

import java.awt.*;

public class HUD {

    public static int health = 100;
    Game game;

    public HUD(Game game) {
        this.game = game;
    }

    public void tick() {
        health = Lib.clamp(health, 0, 200);
        if (health == 0) {
            game.player.doDeath();
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(16, 16, 100, 16);
        g.setColor(new Color(75, 255, 0));
        g.fillRect(16, 16, health, 16);
        g.setColor(Color.white);
        g.drawRect(16, 16, 100, 16);
    }
}
